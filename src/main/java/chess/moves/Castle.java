package chess.moves;

import chess.board.lowlevel.Board;
import chess.Position;

public class Castle implements ExecutableMove {
    private final RawMove kingMove;
    private final RawMove rookMove;
    private final Board board;

    public enum Type {
        SHORT, LONG
    }

    public Castle(RawMove kingMove, RawMove rookMove, Board board) {
        this.kingMove = kingMove;
        this.rookMove = rookMove;
        this.board = board;
    }

    @Override
    public Board makeMove() {
        Board newBoard = Board.getCopy(board);
        newBoard.write(board.read(kingMove.getStartPosition()), kingMove.getEndPosition());
        newBoard.write(Board.EMPTY, kingMove.getStartPosition());
        newBoard.write(board.read(rookMove.getStartPosition()), rookMove.getEndPosition());
        newBoard.write(Board.EMPTY, rookMove.getStartPosition());
        return newBoard;

    }

    @Override
    public Position getStartPosition() {
        return kingMove.getStartPosition();
    }

    public Type getType() {
        if (rookMove.getStartPosition().getX() == 1) {
            return Type.LONG;
        } else {
            return Type.SHORT;
        }
    }


    @Override
    public RawMove getRepresentation() {
        return kingMove;
    }
}
