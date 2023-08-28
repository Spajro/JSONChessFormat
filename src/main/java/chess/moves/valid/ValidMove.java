package chess.moves.valid;

import chess.moves.raw.RawMove;

public interface ValidMove {
    RawMove getRepresentation();

    default boolean isExecutable() {
        return false;
    }
}
