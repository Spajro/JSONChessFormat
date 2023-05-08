package data.model;

import chess.board.ChessBoard;
import chess.moves.ExecutableMove;

public class FENDiagram extends Diagram {
    private final ChessBoard chessBoard;

    public FENDiagram(ExecutableMove creatingMove, Diagram parent, int moveId, ChessBoard chessBoard) {
        super(creatingMove, parent, moveId);
        this.chessBoard = chessBoard;
    }

    @Override
    public ChessBoard getBoard() {
        return chessBoard;
    }
}
