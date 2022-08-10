package data.dts.board;

import data.dts.Position;
import data.dts.color.Color;
import data.dts.fields.*;
import data.dts.pieces.*;

public class BoardWrapper {
    public static Field getFieldFromBoard(ChessBoard chessBoard, Position position) {
        int valueFromBoard = chessBoard.getBoard().read(position);
        return convertIdToField(chessBoard, position, valueFromBoard);
    }

    public static Piece createPieceFromId(ChessBoard chessBoard, int pieceId, Position position) {
        return convertIdToField(chessBoard, position, pieceId).getPiece();
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
