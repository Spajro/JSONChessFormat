package chess.utility;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.moves.*;
import chess.pieces.*;

public class AlgebraicTranslator {
    public static RawMove longAlgebraicToMove(String move, Color color) {
        if (move.equals("O-O")) {
            if (color.isWhite()) {
                return new RawMove(new Position(5, 1), new Position(7, 1));
            } else {
                return new RawMove(new Position(5, 8), new Position(7, 8));
            }
        }
        if (move.equals("O-O-O")) {
            if (color.isWhite()) {
                return new RawMove(new Position(5, 1), new Position(3, 1));
            } else {
                return new RawMove(new Position(5, 8), new Position(3, 8));
            }
        }
        String rawMove = slicePieceId(move);
        if (rawMove.charAt(2) == '-') {
            return new RawMove(algebraicToPosition(rawMove.substring(0, 2)), algebraicToPosition(rawMove.substring(3)));
        }
        throw new IllegalArgumentException(move + "is not a valid long algebraic notation");
    }

    private static String slicePieceId(String move) {
        if (move.length() == 6) {
            return move.substring(1);
        }
        return move;
    }

    private static Position algebraicToPosition(String position) {
        if (Character.getNumericValue(position.charAt(1)) > 8) {
            throw new IllegalArgumentException("Invalid row number:" + Character.getNumericValue(position.charAt(1)));
        }
        return new Position(columnToNumber(position.charAt(0)), Character.getNumericValue(position.charAt(1)));
    }

    public static String moveToLongAlgebraic(ChessBoard board, ValidMove move) {
        if (move instanceof SimpleMove simpleMove) {
            return pieceToAlgebraic(board.getField(simpleMove.getStartPosition()).getPiece()) +
                    positionToAlgebraic(simpleMove.getStartPosition()) +
                    "-" +
                    positionToAlgebraic(simpleMove.getEndPosition());
        } else if (move instanceof Castle castle) {
            return switch (castle.getType()) {
                case SHORT -> "O-O";
                case LONG -> "O-O-O";
            };
        } else if (move instanceof EnPassantCapture enPassantCapture) {
            return positionToAlgebraic(enPassantCapture.getStartPosition()) +
                    "-" +
                    positionToAlgebraic(enPassantCapture.getEndPosition());
        }
        throw new IllegalStateException();
    }

    public static char pieceToAlgebraic(Piece piece) {
        if (piece instanceof Pawn) {
            return ' ';
        } else if (piece instanceof Knight) {
            return 'N';
        } else if (piece instanceof Bishop) {
            return 'B';
        } else if (piece instanceof Rook) {
            return 'R';
        } else if (piece instanceof Queen) {
            return 'Q';
        } else if (piece instanceof King) {
            return 'K';
        }
        throw new IllegalStateException();
    }

    private static String positionToAlgebraic(Position position) {
        return numberToColumn(position.getX()) + String.valueOf(position.getY());
    }

    public static int columnToNumber(char column) {
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

    public static char numberToColumn(int column) {
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


