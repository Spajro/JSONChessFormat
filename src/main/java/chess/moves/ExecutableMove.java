package chess.moves;

import chess.board.lowlevel.Board;

public interface ExecutableMove extends ValidMove{
    Board makeMove();

    @Override
    default boolean isExecutable() {
        return true;
    }
}
