package chess.board.fields;

import chess.Position;
import chess.pieces.*;

public interface Field {

    boolean isEmpty();

    boolean hasPiece();

    Piece getPiece();

    Position getPosition();
}
