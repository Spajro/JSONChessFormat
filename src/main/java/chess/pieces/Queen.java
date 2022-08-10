package chess.pieces;

import chess.board.ChessBoard;
import chess.Position;
import chess.color.Color;

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
