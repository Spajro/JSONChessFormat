package chess.moves;

import chess.board.ChessBoard;
import chess.board.lowlevel.Board;
import chess.color.Color;

import static chess.pieces.Piece.*;

public class Promotion extends RawMove implements ExecutableMove {
    private final ChessBoard chessBoard;
    private final Type type;

    public Promotion(RawMove move, ChessBoard chessBoard, Type type) {
        super(move);
        this.chessBoard = chessBoard;
        this.type = type;
    }

    @Override
    public Board makeMove() {
        Board newBoard = Board.getCopy(chessBoard.getBoard());
        newBoard.write(convert(type,chessBoard.getColor()), getEndPosition());
        newBoard.write(Board.EMPTY, getStartPosition());
        return newBoard;
    }

    private byte convert(Type type, Color color) {
        if(color.isWhite()){
            return switch(type){
                case KNIGHT -> Board.WKNIGHT;
                case BISHOP -> Board.WBISHOP;
                case ROOK -> Board.WROOK;
                case QUEEN -> Board.WQUEEN;
                default -> throw new IllegalStateException("Illegal promotion");
            };
        }
        else{
            return switch(type){
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
        return this;
    }

    public Type getType() {
        return type;
    }
}
