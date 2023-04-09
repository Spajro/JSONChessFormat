package chess.pieces;

import chess.board.ChessBoard;
import chess.board.lowlevel.Field;
import chess.Position;
import chess.color.Color;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Knight extends Piece {
    public Knight(Color color, Position position, ChessBoard chessBoard) {
        super(color, position, chessBoard);
    }

    @Override
    public Set<Position> getPossibleStartPositions() {
        return Steps.knightSteps.stream()
                .map(position::add)
                .map(this::getField)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(field -> field.isEmpty() || field.getPiece().partiallyEquals(this))
                .map(Field::getPosition)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Position> getPossibleEndPositions() {
        return Steps.knightSteps.stream()
                .map(position::add)
                .map(this::getField)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(field -> field.isEmpty() || field.getPiece().getColor().equal(color.swap()))
                .map(Field::getPosition)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Position> getAttackedPositions() {
        return Steps.knightSteps.stream()
                .map(position::add)
                .map(this::getField)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Field::getPosition)
                .collect(Collectors.toSet());
    }

    @Override
    public Type getType() {
        return Type.KNIGHT;
    }

}
