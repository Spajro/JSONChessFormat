package chess.moves;

import chess.Position;
import chess.board.lowlevel.Board;

public class EnPassantCapture extends RawMove implements ExecutableMove {
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
        newBoard.write(Board.EMPTY, new Position(getEndPosition().getX(), getStartPosition().getY()));
        return newBoard;
    }

    @Override
    public RawMove getRepresentation() {
        return this;
    }
}
