package chess.moves.valid.executable;

import chess.Position;
import chess.board.lowlevel.Board;
import chess.moves.Vector;
import chess.moves.raw.RawMove;

public class EnPassantCapture extends Vector implements ExecutableMove {
    private final Board board;

    public EnPassantCapture(RawMove move, Board board) {
        super(move);
        this.board = board;
    }

    @Override
    public Board makeMove() {
        Board newBoard = Board.getCopy(board);
        newBoard.write(board.read(getStartPosition()), getEndPosition());
        newBoard.write(Board.EMPTY, getStartPosition());
        newBoard.write(Board.EMPTY, Position.of(getEndPosition().getX(), getStartPosition().getY()));
        return newBoard;
    }

    @Override
    public RawMove getRepresentation() {
        return RawMove.of(this);
    }
}
