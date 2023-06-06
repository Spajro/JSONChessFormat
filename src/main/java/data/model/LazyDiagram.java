package data.model;

import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import chess.moves.valid.executable.ExecutableMove;
import chess.results.ValidMoveResult;

import java.util.LinkedList;
import java.util.Optional;

public class LazyDiagram extends Diagram {

    private final LinkedList<RawMove> moves;

    public LazyDiagram(ExecutableMove creatingMove, ChessBoard chessBoard, Diagram parent, LinkedList<RawMove> moves) {
        super(creatingMove, chessBoard, parent);
        this.moves = moves;
    }

    @Override
    public LinkedList<Diagram> getNextDiagrams() {
        return expand().getNextDiagrams();
    }

    public Diagram expand() {
        Optional<Diagram> optionalParent = getParent();

        RawMove move = moves.poll();
        ChessBoard chessBoard = getBoard();
        Optional<ValidMoveResult> validMoveResult = chessBoard.makeMove(move).validate(null);
        if (validMoveResult.isEmpty()) {
            throw new IllegalStateException();
        }
        Diagram expanded = new Diagram(
                validMoveResult.get().getExecutableMove(),
                chessBoard,
                optionalParent.orElse(null)
        );

        Diagram lazy = new LazyDiagram(
                validMoveResult.get().getExecutableMove(),
                validMoveResult.get().getResult(),
                expanded,
                moves
        );
        expanded.getNextDiagrams().add(lazy);

        if (optionalParent.isPresent()) {
            Diagram parent = optionalParent.get();
            int index = parent.getNextDiagrams().indexOf(this);
            parent.getNextDiagrams().set(index, expanded);
        }

        return expanded;
    }
}
