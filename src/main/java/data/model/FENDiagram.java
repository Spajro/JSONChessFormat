package data.model;

import chess.board.ChessBoard;
import chess.moves.valid.executable.ExecutableMove;

public class FENDiagram extends Diagram {
    private final ChessBoard chessBoard;

    public FENDiagram(ExecutableMove creatingMove, Diagram parent, ChessBoard chessBoard) {
        super(creatingMove, parent);
        this.chessBoard = chessBoard;
    }

    @Override
    public ChessBoard getBoard() {
        return chessBoard;
    }
}
