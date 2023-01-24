package chess.moves;

import chess.board.Board;

public class SimpleMove extends RawMove implements ValidMove{
    private final Board board;

    public SimpleMove(RawMove move,Board board){
        super(move);
        this.board=board;
    }

    @Override
    public Board makeMove() {
        Board newBoard=Board.getCopy(board);
        newBoard.write(board.read(getStartPosition()), getEndPosition());
        newBoard.write(Board.EMPTY, getStartPosition());
        return newBoard;
    }
}
