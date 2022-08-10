package data.dts.moves;

import data.dts.Position;
import data.dts.board.Board;

public interface ValidMove {
     Board makeMove();

    Position getStartPosition();
}
