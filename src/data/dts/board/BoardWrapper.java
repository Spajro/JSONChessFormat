package src.data.dts.board;

import src.data.dts.Position;
import src.data.dts.color.Color;
import src.data.dts.fields.*;
import src.data.dts.pieces.*;

public class BoardWrapper{
    public static Field getField(ChessBoard chessBoard,Board board,Position position){
        int valueFromBoard=board.read(position);
        return switch (valueFromBoard){
            case Board.EMPTY -> new EmptyField(position,chessBoard);
            case Board.WPAWN -> new Pawn(Color.white,position,chessBoard);
            case Board.WROOK -> new Rook(Color.white,position,chessBoard);
            case Board.WBISHOP-> new Bishop(Color.white,position,chessBoard);
            case Board.WQUEEN -> new Queen(Color.white,position,chessBoard);
            case Board.WKING -> new King(Color.white,position,chessBoard);
            case Board.WKNIGHT -> new Knight(Color.white,position,chessBoard);
            case Board.BPAWN -> new Pawn(Color.black,position,chessBoard);
            case Board.BROOK -> new Rook(Color.black,position,chessBoard);
            case Board.BBISHOP-> new Bishop(Color.black,position,chessBoard);
            case Board.BQUEEN -> new Queen(Color.black,position,chessBoard);
            case Board.BKING -> new King(Color.black,position,chessBoard);
            case Board.BKNIGHT -> new Knight(Color.black,position,chessBoard);
            default -> throw new IllegalArgumentException("unknown board figure id");
        };
    }
}
