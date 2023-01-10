package chess.board;

import chess.fields.EmptyField;
import chess.fields.Field;
import chess.pieces.*;
import chess.Position;
import chess.color.Color;

public class BoardWrapper {
    public static Field getFieldFromBoard(ChessBoard chessBoard, Position position) {
        int valueFromBoard = chessBoard.getBoard().read(position);
        return convertIdToField(chessBoard, position, valueFromBoard);
    }

    public static int getBoardIdFromPiece(Piece piece){
        if (piece instanceof Pawn) {
            return piece.getColor().isWhite() ? Board.WPAWN : Board.BPAWN;
        }
        if (piece instanceof Knight) {
            return piece.getColor().isWhite() ? Board.WKNIGHT : Board.BKNIGHT;
        }
        if (piece instanceof Bishop) {
            return piece.getColor().isWhite() ? Board.WBISHOP : Board.BBISHOP;
        }
        if (piece instanceof Rook) {
            return piece.getColor().isWhite() ? Board.WROOK : Board.BROOK;
        }
        if (piece instanceof Queen) {
            return piece.getColor().isWhite() ? Board.WQUEEN : Board.BQUEEN;
        }
        if (piece instanceof King) {
            return piece.getColor().isWhite() ? Board.WKING : Board.BKING;
        }
        throw new IllegalStateException("Should not be throwed");
    }

    private static Field convertIdToField(ChessBoard chessBoard, Position position, int id) {
        return switch (id) {
            case Board.EMPTY -> new EmptyField(position, chessBoard);
            case Board.WPAWN -> new Pawn(Color.white, position, chessBoard);
            case Board.WROOK -> new Rook(Color.white, position, chessBoard);
            case Board.WBISHOP -> new Bishop(Color.white, position, chessBoard);
            case Board.WQUEEN -> new Queen(Color.white, position, chessBoard);
            case Board.WKING -> new King(Color.white, position, chessBoard);
            case Board.WKNIGHT -> new Knight(Color.white, position, chessBoard);
            case Board.BPAWN -> new Pawn(Color.black, position, chessBoard);
            case Board.BROOK -> new Rook(Color.black, position, chessBoard);
            case Board.BBISHOP -> new Bishop(Color.black, position, chessBoard);
            case Board.BQUEEN -> new Queen(Color.black, position, chessBoard);
            case Board.BKING -> new King(Color.black, position, chessBoard);
            case Board.BKNIGHT -> new Knight(Color.black, position, chessBoard);
            default -> throw new IllegalArgumentException("unknown board figure id");
        };
    }
}
