package data.model;

import chess.board.ChessBoard;
import chess.moves.raw.RawMove;

public class FENDiagram extends Diagram {
    private final ChessBoard chessBoard;

    public FENDiagram(RawMove creatingMove, ChessBoard chessBoard) {
        super(creatingMove, chessBoard, null);
        this.chessBoard = chessBoard;
    }

    @Override
    public ChessBoard getBoard() {
        return chessBoard;
    }
}
