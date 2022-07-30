package src.data.dts.pieces;

import src.data.dts.Position;
import src.data.dts.board.ChessBoard;
import src.data.dts.color.Color;

import java.util.Set;

public class Bishop extends RestrictedMovementPiece {
    public Bishop(Color color, Position position, ChessBoard chessBoard) {
        super(color, position, chessBoard);
    }

    @Override
    public Set<Position> getPossibleStartPositions() {
        return getPossibleStartPositions(Steps.diagonalSteps);
    }

    @Override
    public Set<Position> getPossibleEndPositions() {
        return getPossibleEndPositions(Steps.diagonalSteps);
    }
}
