package chess.moves.valid.executable;

import chess.board.lowlevel.Board;
import chess.moves.Vector;
import chess.moves.raw.RawMove;

public class SimpleMove extends Vector implements ExecutableMove {
    private final Board board;

    public SimpleMove(RawMove move, Board board) {
        super(move);
        this.board = board;
    }

    @Override
    public Board makeMove() {
        Board newBoard = Board.getCopy(board);
        newBoard.write(board.read(getStartPosition()), getEndPosition());
        newBoard.write(Board.EMPTY, getStartPosition());
        return newBoard;
    }

    @Override
    public RawMove getRepresentation() {
        return new RawMove(this);
    }
}
