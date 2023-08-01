package data.pgn;

import chess.board.ChessBoard;
import chess.moves.valid.executable.ExecutableMove;
import chess.formats.algebraic.ShortAlgebraicParser;
import data.MoveParser;
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
    private final MoveParser moveParser = MoveParser.getInstance();

    public List<ParsedPGN> parsePGN(String pgn) {
        String newLine = getEndLineCharacter(pgn).orElseThrow();
        long startTime = System.nanoTime();
        ArrayList<String> parts = new ArrayList<>(List.of(pgn.split(newLine + newLine)));
        ArrayList<ParsedPGN> result = new ArrayList<>();
        for (int i = 0; i < parts.size() - 1; i += 2) {
            Optional<ArrayDeque<ExecutableMove>> moves = parseMoves(parts.get(i + 1));
            int length;
            if (moves.isEmpty()) {
                length = -1;
            } else {
                length = moves.get().size();
            }
            result.add(new ParsedPGN(
                    parseMetadata(parts.get(i), length),
                    moves
            ));
        }
        long endTime = System.nanoTime();
        long nanoDuration = (endTime - startTime);
        double secondDuration = ((double) nanoDuration / Math.pow(10, 9));
        double nodesPerSec = ((double) (parts.size() / 2) / secondDuration);
        Log.debug("Parsing time: " + secondDuration + "s with speed: " + nodesPerSec + "gps"); //TODO FOR DEBUG
        return result;
    }

    public MetaData parseMetadata(String metadata, int gameLength) {
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

    public Optional<ArrayDeque<ExecutableMove>> parseMoves(String input) {
        if (input.charAt(0) == ' ') {
            input = input.substring(1);
        }
        Pattern pattern = Pattern.compile("[^A-Za-z\\d-/+#=]");
        List<String> moves = Arrays.stream(input.split(" "))
                .map(this::removeMarksFromEndIfAny)
                .filter(string -> !pattern.matcher(string).find())
                .map(String::trim)
                .toList();
        return moveParser.parseMoves(new ChessBoard(), moves, shortAlgebraicParser::parseShortAlgebraic);
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

    public Optional<String> getEndLineCharacter(String text) {
        if (text.contains("\r\n")) {
            return Optional.of("\r\n");
        } else if (text.contains("\n")) {
            return Optional.of("\n");
        } else {
            return Optional.empty();
        }
    }
}
