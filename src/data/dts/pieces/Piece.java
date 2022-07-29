package src.data.dts.pieces;

import src.data.dts.Position;
import src.data.dts.board.ChessBoard;
import src.data.dts.color.Color;

import java.util.Set;

public abstract class Piece {
    private final Color color;
    private final Position position;
    private final ChessBoard chessBoard;

    public Piece(Color color, Position position, ChessBoard chessBoard) {
        this.color = color;
        this.position = position;
        this.chessBoard = chessBoard;
    }

    public abstract Set<Position> getPossibleStartPositions();

    public abstract Set<Position> getPossibleEndPositions();
}
