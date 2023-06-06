package data.pgn;

import chess.board.ChessBoard;
import chess.utility.ShortAlgebraicParser;
import data.ParserUtility;
import data.model.Diagram;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;
import log.Log;

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
        long startTime = System.nanoTime();
        ArrayList<String> parts = new ArrayList<>(List.of(pgn.split(newLine + newLine)));
        ArrayList<ParsedPGN> result = new ArrayList<>();
        for (int i = 0; i < parts.size() - 1; i += 2) {
            Optional<Diagram> diagram = parseMoves(parts.get(i + 1));
            int length;
            if (diagram.isEmpty()) {
                length = -1;
            } else {
                length = treeLength(diagram.get()).orElseThrow();
            }
            result.add(new ParsedPGN(
                    parseMetadata(parts.get(i), length),
                    diagram
            ));
        }
        long endTime = System.nanoTime();
        long nanoDuration = (endTime - startTime);
        double secondDuration = ((double) nanoDuration / Math.pow(10, 9));
        double nodesPerSec = ((double) (parts.size() / 2) / secondDuration);
        Log.debug("Parsing time: " + secondDuration + "s with speed: " + nodesPerSec + "gps"); //TODO FOR DEBUG
        return result;
    }

    private MetaData parseMetadata(String metadata, int gameLength) {
        HashMap<String, String> metadataMap = new HashMap<>();
        Arrays.stream(metadata.split(getEndLineCharacter(metadata).orElseThrow()))
                .map(s -> s.substring(1, s.length() - 1))
                .forEach(s -> {
                    int index = s.indexOf(" ");
                    metadataMap.put(s.substring(0, index), s.substring(index));
                });
        return new GameData(
                metadataMap.get("Event"),
                metadataMap.get("Site"),
                metadataMap.get("Date"),
                metadataMap.get("Round"),
                metadataMap.get("White"),
                metadataMap.get("Black"),
                metadataMap.get("Result"),
                gameLength
        );
    }

    private Optional<Diagram> parseMoves(String input) {
        Pattern pattern = Pattern.compile("[^A-Za-z\\d-/+#=]");
        List<String> moves = Arrays.stream(input.split(" "))
                .map(this::removeMarksFromEndIfAny)
                .filter(string -> !pattern.matcher(string).find())
                .map(String::trim)
                .toList();
        return parserUtility.parseMoves(new ChessBoard(), moves, shortAlgebraicParser::parseShortAlgebraic);
    }

    private Optional<Integer> treeLength(Diagram diagram) {
        return switch (diagram.getNextDiagrams().size()) {
            case 0 -> Optional.of(1);
            case 1 -> treeLength(diagram.getNextDiagrams().getFirst()).map(i -> i + 1);
            default -> Optional.empty();
        };
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
