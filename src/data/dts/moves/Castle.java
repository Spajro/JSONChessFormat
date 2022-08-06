package data.dts.moves;

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
        newBoard.write(board.read(kingMove.getOldPosition()),kingMove.getNewPosition());
        newBoard.write(Board.EMPTY,kingMove.getOldPosition());
        newBoard.write(board.read(rookMove.getOldPosition()),rookMove.getNewPosition());
        newBoard.write(Board.EMPTY,rookMove.getOldPosition());
        return newBoard;

    }
}
