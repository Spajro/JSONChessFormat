package chess.pieces;

import chess.board.ChessBoard;
import chess.Position;
import chess.color.Color;

import java.util.Set;

public class Queen extends RestrictedMovementPiece {
    public Queen(Color color, Position position) {
        super(color, position);
    }

    @Override
    public Set<Position> getPossibleStartPositions(ChessBoard chessBoard) {
        return getPossibleStartPositions(chessBoard, Steps.fullSteps);
    }

    @Override
    public Set<Position> getPossibleEndPositions(ChessBoard chessBoard) {
        return getPossibleEndPositions(chessBoard, Steps.fullSteps);
    }

    @Override
    public Set<Position> getAttackedPositions(ChessBoard chessBoard) {
        return getAttackedPositions(chessBoard, Steps.fullSteps);
    }

    @Override
    public Type getType() {
        return Type.QUEEN;
    }
}
