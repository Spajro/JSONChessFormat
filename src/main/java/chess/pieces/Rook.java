package chess.pieces;

import chess.board.ChessBoard;
import chess.Position;
import chess.color.Color;

import java.util.Set;

public class Rook extends RestrictedMovementPiece {
    public Rook(Color color, Position position, ChessBoard chessBoard) {
        super(color, position, chessBoard);
    }

    @Override
    public Set<Position> getPossibleStartPositions() {
        return getPossibleStartPositions(Steps.basicSteps);
    }

    @Override
    public Set<Position> getPossibleEndPositions() {
        return getPossibleEndPositions(Steps.basicSteps);
    }

    @Override
    public Set<Position> getAttackedPositions() {
        return getAttackedPositions(Steps.basicSteps);
    }

    @Override
    public Type getType() {
        return Type.ROOK;
    }
}
