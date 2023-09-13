package chess.formats.algebraic;

import chess.Position;
import chess.color.Color;
import chess.moves.raw.RawMove;

import java.util.Optional;

import static chess.pieces.Piece.*;
import static chess.pieces.Piece.Type.*;

public class AlgebraicUtility {
    private static final AlgebraicUtility algebraicUtility = new AlgebraicUtility();

    private AlgebraicUtility() {
    }

    public static AlgebraicUtility getInstance() {
        return algebraicUtility;
    }

    Optional<RawMove> algebraicToCastle(String move, Color color) {
        if (move.equals("O-O")) {
            if (color.isWhite()) {
                return Optional.of(RawMove.of(Position.of(5, 1), Position.of(7, 1)));
            } else {
                return Optional.of(RawMove.of(Position.of(5, 8), Position.of(7, 8)));
            }
        }
        if (move.equals("O-O-O")) {
            if (color.isWhite()) {
                return Optional.of(RawMove.of(Position.of(5, 1), Position.of(3, 1)));
            } else {
                return Optional.of(RawMove.of(Position.of(5, 8), Position.of(3, 8)));
            }
        }
        return Optional.empty();
    }

    Optional<String> castleToAlgebraic(RawMove rawMove) {
        if (rawMove.equals(RawMove.of(Position.of(5, 1), Position.of(7, 1)))
                || rawMove.equals(RawMove.of(Position.of(5, 8), Position.of(7, 8)))) {
            return Optional.of("O-O");
        }
        if (rawMove.equals(RawMove.of(Position.of(5, 1), Position.of(3, 1)))
                || rawMove.equals(RawMove.of(Position.of(5, 8), Position.of(3, 8)))) {
            return Optional.of("O-O-O");
        }
        return Optional.empty();
    }

    public Optional<Position> algebraicToPosition(String position) {
        if (Character.getNumericValue(position.charAt(1)) > 8) {
            return Optional.empty();
        }
        Optional<Integer> column = columnToNumber(position.charAt(0));
        return column.map(integer -> Position.of(integer, Character.getNumericValue(position.charAt(1))));
    }

    public Optional<Integer> columnToNumber(char column) {
        return Optional.ofNullable(switch (column) {
            case 'a' -> 1;
            case 'b' -> 2;
            case 'c' -> 3;
            case 'd' -> 4;
            case 'e' -> 5;
            case 'f' -> 6;
            case 'g' -> 7;
            case 'h' -> 8;
            default -> null;
        });
    }

    public char typeToAlgebraic(Type type) {
        return switch (type) {
            case PAWN -> ' ';
            case KNIGHT -> 'N';
            case BISHOP -> 'B';
            case ROOK -> 'R';
            case QUEEN -> 'Q';
            case KING -> 'K';
        };
    }

    public Optional<Type> algebraicToType(char type) {
        return Optional.ofNullable(switch (type) {
            case ' ' -> PAWN;
            case 'N' -> KNIGHT;
            case 'B' -> BISHOP;
            case 'R' -> ROOK;
            case 'Q' -> QUEEN;
            case 'K' -> KING;
            default -> null;
        });
    }

    public String positionToAlgebraic(Position position) {
        return numberToColumn(position.getX()) + String.valueOf(position.getY());
    }

    public char numberToColumn(int column) {
        return switch (column) {
            case 1 -> 'a';
            case 2 -> 'b';
            case 3 -> 'c';
            case 4 -> 'd';
            case 5 -> 'e';
            case 6 -> 'f';
            case 7 -> 'g';
            case 8 -> 'h';
            default -> throw new IllegalArgumentException("Not valid column:" + column);
        };
    }
}
