package data;

import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import chess.moves.valid.executable.ExecutableMove;
import chess.results.MoveResult;
import chess.results.ValidMoveResult;
import chess.utility.AlgebraicUtility;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public class MoveParser {
    private static final MoveParser MOVE_PARSER = new MoveParser();

    private MoveParser() {
    }

    public static MoveParser getInstance() {
        return MOVE_PARSER;
    }

    public Optional<LinkedList<ExecutableMove>> parseMoves(ChessBoard chessBoard, List<String> moves, BiFunction<String, ChessBoard, RawMove> parser) {
        LinkedList<ExecutableMove> result=new LinkedList<>();
        for (String move : moves) {
            if (isGameEnd(move)) {
                break;
            }
            RawMove rawMove = parser.apply(move, chessBoard);
            MoveResult moveResult = chessBoard.makeMove(rawMove);
            Optional<ValidMoveResult> validMoveResult = moveResult.validate(() -> AlgebraicUtility.getInstance().parsePromotion(move).orElseThrow());
            if (validMoveResult.isPresent()) {
                chessBoard = validMoveResult.get().getResult();
                result.add(validMoveResult.get().getExecutableMove());
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(result);
    }

    private boolean isGameEnd(String move) {
        return move.equals("1-0") || move.equals("0-1") || move.equals("1/2-1/2");
    }
}