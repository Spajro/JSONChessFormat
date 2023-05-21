package data.pgn;

import chess.moves.RawMove;
import chess.utility.ShortAlgebraicParser;
import data.ParserUtility;
import data.model.Diagram;
import data.model.MetaData;

import java.util.*;
import java.util.regex.Pattern;

public class PGNParser {

    private static final PGNParser pgnParser = new PGNParser();

    private PGNParser() {
    }

    public static PGNParser getInstance() {
        return pgnParser;
    }

    private final ShortAlgebraicParser shortAlgebraicParser = new ShortAlgebraicParser();
    private final ParserUtility parserUtility = ParserUtility.getInstance();

    public List<ParsedPGN> parsePGN(String pgn) {
        String newLine = getEndLineCharacter(pgn).orElseThrow();
        ArrayList<String> parts = new ArrayList<>(List.of(pgn.split(newLine + newLine)));
        ArrayList<ParsedPGN> result = new ArrayList<>();
        for (int i = 0; i < parts.size() - 1; i += 2) {
            result.add(new ParsedPGN(
                    parseMetadata(parts.get(i)),
                    parseMoves(parts.get(i + 1)
                    )));
        }
        return result;
    }

    private MetaData parseMetadata(String metadata) {
        HashMap<String, String> metadataMap = new HashMap<>();
        Arrays.stream(metadata.split(getEndLineCharacter(metadata).orElseThrow()))
                .map(s -> s.substring(1, s.length() - 1))
                .forEach(s -> {
                    int index = s.indexOf(" ");
                    metadataMap.put(s.substring(0, index), s.substring(index));
                });
        return new MetaData(
                metadataMap.get("Event"),
                metadataMap.get("Site"),
                metadataMap.get("Date"),
                metadataMap.get("Round"),
                metadataMap.get("White"),
                metadataMap.get("Black"),
                metadataMap.get("Result")
        );
    }

    private Optional<Diagram> parseMoves(String input) {
        Pattern pattern = Pattern.compile("[^A-Za-z\\d-/+#=]");
        List<String> moves = Arrays.stream(input.split(" "))
                .map(this::removeMarksFromEndIfAny)
                .filter(string -> !pattern.matcher(string).find())
                .map(String::trim)
                .toList();
        Diagram root = new Diagram();
        Optional<List<RawMove>> optionalRawMoves = parserUtility.parseMoves(root, moves, shortAlgebraicParser::parseShortAlgebraic);
        return optionalRawMoves.map(moveList -> parserUtility.createTree(root, moveList));
    }

    private String removeMarksFromEndIfAny(String string) {
        int index = string.length() - 1;
        while (string.charAt(index) == '?' || string.charAt(index) == '!') {
            index--;
            if (index < 0) {
                return "";
            }
        }
        return string.substring(0, index + 1);
    }

    private Optional<String> getEndLineCharacter(String text) {
        if (text.contains("\r\n")) {
            return Optional.of("\r\n");
        } else if (text.contains("\n")) {
            return Optional.of("\n");
        } else {
            return Optional.empty();
        }
    }
}
