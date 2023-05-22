package data;

import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import chess.results.MoveResult;
import chess.results.ValidMoveResult;
import chess.utility.AlgebraicUtility;
import data.model.Diagram;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public class ParserUtility {
    private static final ParserUtility parserUtility = new ParserUtility();

    private ParserUtility() {
    }

    public static ParserUtility getInstance() {
        return parserUtility;
    }

    public Optional<List<RawMove>> parseMoves(Diagram node, List<String> moves, BiFunction<String, ChessBoard, RawMove> parser) {
        ChessBoard chessBoard = node.getBoard();
        List<RawMove> result = new LinkedList<>();
        for (String move : moves) {
            if (isGameEnd(move)) {
                return Optional.of(result);
            }
            RawMove rawMove = parser.apply(move, chessBoard);
            result.add(rawMove);
            MoveResult moveResult = chessBoard.makeMove(rawMove);
            Optional<ValidMoveResult> validMoveResult = moveResult.validate(() -> AlgebraicUtility.getInstance().parsePromotion(move).orElseThrow());
            if (validMoveResult.isPresent()) {
                chessBoard = validMoveResult.get().getResult();
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(result);
    }

    public Diagram createTree(Diagram node, List<RawMove> moveList) {
        Diagram tempNode = node;
        for (RawMove rawMove : moveList) {
            tempNode = tempNode.makeMove(rawMove, null);
        }
        return node;
    }

    private boolean isGameEnd(String move) {
        return move.equals("1-0") || move.equals("0-1") || move.equals("1/2-1/2");
    }
}
