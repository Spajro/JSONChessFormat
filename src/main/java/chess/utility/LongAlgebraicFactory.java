package chess.utility;

import chess.Position;
import chess.board.ChessBoard;
import chess.moves.*;
import chess.pieces.*;

public class LongAlgebraicFactory {

    public String moveToLongAlgebraic(ChessBoard board, ValidMove move) {
        if (move instanceof SimpleMove simpleMove) {
            return typeToAlgebraic(board.getField(simpleMove.getStartPosition()).getPiece().getType()) +
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
        } else if (move instanceof Promotion promotion) {
            return positionToAlgebraic(promotion.getStartPosition()) +
                    "-" +
                    positionToAlgebraic(promotion.getEndPosition()) +
                    "=" +
                    typeToAlgebraic(promotion.getType());
        }
        throw new IllegalStateException();
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

    private String positionToAlgebraic(Position position) {
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
