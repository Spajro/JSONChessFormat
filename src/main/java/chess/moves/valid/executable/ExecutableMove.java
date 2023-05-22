package chess.moves.valid.executable;

import chess.board.lowlevel.Board;
import chess.moves.valid.ValidMove;

public interface ExecutableMove extends ValidMove {
    Board makeMove();

    @Override
    default boolean isExecutable() {
        return true;
    }
}
