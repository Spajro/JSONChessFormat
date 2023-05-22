package chess.moves.valid;

import chess.Position;
import chess.moves.raw.RawMove;

public interface ValidMove {
    Position getStartPosition();

    RawMove getRepresentation();

    default boolean isExecutable() {
        return false;
    }
}
