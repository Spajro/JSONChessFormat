package chess.utility;

import chess.Position;
import chess.color.Color;
import chess.moves.RawMove;

import java.util.Optional;

public class AlgebraicParser {
    private static final AlgebraicParser algebraicParser = new AlgebraicParser();

    public static AlgebraicParser getInstance() {
        return algebraicParser;
    }

    private AlgebraicParser() {
    }

    public RawMove parseLongAlgebraic(String move, Color color) {
        return algebraicCastleToMove(move, color)
                .orElse(longAlgebraicToMove(move).orElseThrow());
    }

    private Optional<RawMove> longAlgebraicToMove(String move) {
        String rawMove = slicePieceId(move);
        if (rawMove.charAt(2) == '-') {
            return Optional.of(new RawMove(
                    algebraicToPosition(rawMove.substring(0, 2)),
                    algebraicToPosition(rawMove.substring(3))));
        }
        return Optional.empty();
    }

    private Optional<RawMove> algebraicCastleToMove(String move, Color color) {
        if (move.equals("O-O")) {
            if (color.isWhite()) {
                return Optional.of(new RawMove(new Position(5, 1), new Position(7, 1)));
            } else {
                return Optional.of(new RawMove(new Position(5, 8), new Position(7, 8)));
            }
        }
        if (move.equals("O-O-O")) {
            if (color.isWhite()) {
                return Optional.of(new RawMove(new Position(5, 1), new Position(3, 1)));
            } else {
                return Optional.of(new RawMove(new Position(5, 8), new Position(3, 8)));
            }
        }
        return Optional.empty();
    }

    private String slicePieceId(String move) {
        if (move.length() == 6) {
            return move.substring(1);
        }
        return move;
    }

    private Position algebraicToPosition(String position) {
        if (Character.getNumericValue(position.charAt(1)) > 8) {
            throw new IllegalArgumentException("Invalid row number:" + Character.getNumericValue(position.charAt(1)));
        }
        return new Position(columnToNumber(position.charAt(0)), Character.getNumericValue(position.charAt(1)));
    }

    private int columnToNumber(char column) {
        return switch (column) {
            case 'a' -> 1;
            case 'b' -> 2;
            case 'c' -> 3;
            case 'd' -> 4;
            case 'e' -> 5;
            case 'f' -> 6;
            case 'g' -> 7;
            case 'h' -> 8;
            default -> throw new IllegalArgumentException("Not valid column:" + column);
        };
    }
}
