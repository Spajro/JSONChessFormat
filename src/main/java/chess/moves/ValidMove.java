package chess.moves;

import chess.Position;
import chess.board.lowlevel.Board;

public interface ValidMove {
    Board makeMove();

    Position getStartPosition();

    RawMove getRepresentation();
}
