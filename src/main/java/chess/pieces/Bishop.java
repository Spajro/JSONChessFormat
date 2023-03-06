package chess.pieces;

import chess.board.ChessBoard;
import chess.Position;
import chess.color.Color;

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

    @Override
    public Set<Position> getAttackedPositions() {
        return getAttackedPositions(Steps.diagonalSteps);
    }
}
