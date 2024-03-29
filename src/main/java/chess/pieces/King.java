package chess.pieces;

import chess.board.ChessBoard;
import chess.board.fields.Field;
import chess.Position;
import chess.color.Color;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class King extends Piece {
    public King(Color color, Position position) {
        super(color, position);
    }

    @Override
    public Set<Position> getPossibleStartPositions(ChessBoard chessBoard) {
        return Steps.fullSteps.stream()
                .map(position::add)
                .map(p -> getField(chessBoard, p))
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
        return Steps.fullSteps.stream()
                .map(position::add)
                .map(p -> getField(chessBoard, p))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(field -> {
                    if (field.isEmpty()) {
                        return true;
                    } else {
                        Piece piece = field.getPiece();
                        return piece.color.equal(color.swap());
                    }
                })
                .map(Field::getPosition)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Position> getAttackedPositions(ChessBoard chessBoard) {
        return Steps.fullSteps.stream()
                .map(position::add)
                .map(p -> getField(chessBoard, p))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Field::getPosition)
                .collect(Collectors.toSet());
    }

    @Override
    public Type getType() {
        return Type.KING;
    }
}
