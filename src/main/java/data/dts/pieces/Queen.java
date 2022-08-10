package data.dts.pieces;

import data.dts.Position;
import data.dts.board.ChessBoard;
import data.dts.color.Color;

import java.util.Set;

public class Queen extends RestrictedMovementPiece {
    public Queen(Color color, Position position, ChessBoard chessBoard) {
        super(color, position, chessBoard);
    }

    @Override
    public Set<Position> getPossibleStartPositions() {
        return getPossibleStartPositions(Steps.fullSteps);
    }

    @Override
    public Set<Position> getPossibleEndPositions() {
        return getPossibleEndPositions(Steps.fullSteps);
    }
}
