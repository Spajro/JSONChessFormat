package data.dts.moves;

import data.dts.Position;
import data.dts.board.Board;

public class Castle implements ValidMove{
    private final RawMove kingMove;
    private final RawMove rookMove;
    private final Board board;

    public Castle(RawMove kingMove, RawMove rookMove, Board board) {
        this.kingMove = kingMove;
        this.rookMove = rookMove;
        this.board = board;
    }

    @Override
    public Board makeMove() {
        Board newBoard=Board.getCopy(board);
        newBoard.write(board.read(kingMove.getStartPosition()),kingMove.getEndPosition());
        newBoard.write(Board.EMPTY,kingMove.getStartPosition());
        newBoard.write(board.read(rookMove.getStartPosition()),rookMove.getEndPosition());
        newBoard.write(Board.EMPTY,rookMove.getStartPosition());
        return newBoard;

    }

    @Override
    public Position getStartPosition() {
        return kingMove.getStartPosition();
    }
}
