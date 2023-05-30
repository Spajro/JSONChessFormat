package data;

import chess.board.ChessBoard;
import chess.moves.raw.RawMove;
import chess.results.MoveResult;
import chess.results.ValidMoveResult;
import chess.utility.AlgebraicUtility;
import data.model.Diagram;

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

    public Optional<Diagram> parseMoves(ChessBoard chessBoard, List<String> moves, BiFunction<String, ChessBoard, RawMove> parser) {
        Diagram temp = new Diagram();
        for (String move : moves) {
            if (isGameEnd(move)) {
                break;
            }
            RawMove rawMove = parser.apply(move, chessBoard);
            MoveResult moveResult = chessBoard.makeMove(rawMove);
            Optional<ValidMoveResult> validMoveResult = moveResult.validate(() -> AlgebraicUtility.getInstance().parsePromotion(move).orElseThrow());
            if (validMoveResult.isPresent()) {
                Diagram diagram = new Diagram(validMoveResult.get().getExecutableMove(), chessBoard, temp);
                chessBoard = validMoveResult.get().getResult();
                temp.getNextDiagrams().add(diagram);
                temp = diagram;
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(temp.getRoot().getNextDiagram(0));
    }

    private boolean isGameEnd(String move) {
        return move.equals("1-0") || move.equals("0-1") || move.equals("1/2-1/2");
    }
}
