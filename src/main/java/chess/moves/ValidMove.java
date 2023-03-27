package chess.moves;

import chess.Position;
import chess.board.lowlevel.Board;

public interface ValidMove {
    Position getStartPosition();

    RawMove getRepresentation();

     default boolean isExecutable(){
        return false;
    }
}
