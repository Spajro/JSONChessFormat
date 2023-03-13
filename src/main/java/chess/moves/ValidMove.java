package chess.moves;

import chess.Position;
import chess.board.Board;

public interface ValidMove {
    Board makeMove();

    Position getStartPosition();

    RawMove getRepresentation();
}
