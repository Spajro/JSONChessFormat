package chess.utility;

import chess.Position;
import chess.color.Color;
import chess.moves.RawMove;
import chess.pieces.Piece;

import java.util.Optional;

public class AlgebraicUtility {
    Optional<RawMove> algebraicCastleToMove(String move, Color color) {
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

    public Position algebraicToPosition(String position) {
        if (Character.getNumericValue(position.charAt(1)) > 8) {
            throw new IllegalArgumentException("Invalid row number:" + Character.getNumericValue(position.charAt(1)));
        }
        return new Position(columnToNumber(position.charAt(0)), Character.getNumericValue(position.charAt(1)));
    }

    public int columnToNumber(char column) {
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

    public char typeToAlgebraic(Piece.Type type) {
        return switch (type) {
            case PAWN -> ' ';
            case KNIGHT -> 'N';
            case BISHOP -> 'B';
            case ROOK -> 'R';
            case QUEEN -> 'Q';
            case KING -> 'K';
        };
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
