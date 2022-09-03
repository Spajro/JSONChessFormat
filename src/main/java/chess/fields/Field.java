package chess.fields;

import chess.board.ChessBoard;
import chess.Position;
import chess.color.Color;
import chess.pieces.*;

import java.util.List;
import java.util.stream.Stream;

public abstract class Field {
    protected final Position position;
    protected final ChessBoard chessBoard;

    public Field(Position position, ChessBoard chessBoard) {
        this.position = position;
        this.chessBoard = chessBoard;
    }

    public abstract boolean isEmpty();

    public abstract boolean hasPiece();

    public abstract Piece getPiece();

    public boolean isAttackedByColor(Color color) {
        List<Piece> list = Stream.of(
                new Pawn(color, position, chessBoard),
                new Knight(color, position, chessBoard),
                new Bishop(color, position, chessBoard),
                new Rook(color, position, chessBoard),
                new Queen(color, position, chessBoard),
                new King(color, position, chessBoard)
        ).toList();

        for (Piece piece : list) {
            int size = piece.getPossibleStartPositions().stream()
                    .map(chessBoard::getField)
                    .filter(Field::hasPiece)
                    .map(Field::getPiece)
                    .map(Piece::getClass)
                    .filter(aClass -> aClass.equals(piece.getClass()))
                    .toList()
                    .size();
            if (size > 0) {
                return true;
            }
        }
        return false;
    }

    public Position getPosition() {
        return position;
    }
}
