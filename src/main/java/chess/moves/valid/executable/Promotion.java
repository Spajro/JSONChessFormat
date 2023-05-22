package chess.moves.valid.executable;

import chess.board.lowlevel.Board;
import chess.color.Color;
import chess.moves.Vector;
import chess.moves.raw.RawMove;
import chess.moves.raw.RawPromotion;

import static chess.pieces.Piece.*;

public class Promotion extends Vector implements ExecutableMove {
    private final Board board;
    private final Color color;
    private final Type type;

    public Promotion(Vector move, Board board, Color color, Type type) {
        super(move);
        this.board = board;
        this.color = color;
        this.type = type;
    }

    @Override
    public Board makeMove() {
        Board newBoard = Board.getCopy(board);
        newBoard.write(convert(type, color), getEndPosition());
        newBoard.write(Board.EMPTY, getStartPosition());
        return newBoard;
    }

    private byte convert(Type type, Color color) {
        if (color.isWhite()) {
            return switch (type) {
                case KNIGHT -> Board.WKNIGHT;
                case BISHOP -> Board.WBISHOP;
                case ROOK -> Board.WROOK;
                case QUEEN -> Board.WQUEEN;
                default -> throw new IllegalStateException("Illegal promotion");
            };
        } else {
            return switch (type) {
                case KNIGHT -> Board.BKNIGHT;
                case BISHOP -> Board.BBISHOP;
                case ROOK -> Board.BROOK;
                case QUEEN -> Board.BQUEEN;
                default -> throw new IllegalStateException("Illegal promotion");
            };
        }
    }

    @Override
    public RawMove getRepresentation() {
        return new RawPromotion(new RawMove(this), type);
    }

    public Type getType() {
        return type;
    }
}
