package chess.pieces;

import chess.board.ChessBoard;
import chess.board.fields.Field;
import chess.Position;
import chess.color.Color;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Knight extends Piece {
    public Knight(Color color, Position position) {
        super(color, position);
    }

    @Override
    public Set<Position> getPossibleStartPositions(ChessBoard chessBoard) {
        return Steps.knightSteps.stream()
                .map(position::add)
                .map(value -> getField(chessBoard, value))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Field::hasPiece)
                .map(Field::getPiece)
                .filter(piece -> piece.partiallyEquals(this))
                .map(Piece::getPosition)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Position> getPossibleEndPositions(ChessBoard chessBoard) {
        return Steps.knightSteps.stream()
                .map(position::add)
                .map(value -> getField(chessBoard, value))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(field -> field.isEmpty() || field.getPiece().getColor().equal(color.swap()))
                .map(Field::getPosition)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Position> getAttackedPositions(ChessBoard chessBoard) {
        return Steps.knightSteps.stream()
                .map(position::add)
                .map(p -> getField(chessBoard, p))
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
