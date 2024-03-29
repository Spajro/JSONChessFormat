package chess;

import chess.board.ChessBoard;
import chess.moves.valid.executable.*;
import chess.formats.fen.FENParser;
import chess.formats.algebraic.LongAlgebraicFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
public class DebugPerftTest {
    Map<String, Long> debugPerft(ChessBoard board, int n) {
        if (n == 1) {
            return listToMoveTypesMap(board.getGenerator().getAllPossibleExecutableMoves());
        } else {
            Map<String, Long> result = Map.of("S", 0L, "E", 0L, "C", 0L, "P", 0L);
            List<Map<String, Long>> list = board.getGenerator().getAllPossibleExecutableMoves().stream()
                    .map(move -> debugPerft(board.makeMove(move), n - 1)).toList();
            for (Map<String, Long> map : list) {
                result = add(result, map);
            }
            return result;
        }
    }

    long divide(ChessBoard board, int n, boolean print) {
        LongAlgebraicFactory longAlgebraicFactory = LongAlgebraicFactory.getInstance();
        if (n < 2) {
            return -1;
        }
        long result = 0;
        for (ExecutableMove move : board.getGenerator().getAllPossibleExecutableMoves()) {
            long temp = divide(board.makeMove(move), n - 1, false);
            if (print) {
                System.out.println(longAlgebraicFactory.moveToLongAlgebraic(board, move) + " : " + temp);
            }
            result += temp;
        }
        return result;
    }

    Map<String, Long> listToMoveTypesMap(List<ExecutableMove> moves) {
        Map<String, Long> map = moves.stream()
                .map(move -> {
                    if (move instanceof SimpleMove) {
                        return "S";
                    }
                    if (move instanceof EnPassantCapture) {
                        return "E";
                    }
                    if (move instanceof Castle) {
                        return "C";
                    }
                    if (move instanceof Promotion) {
                        return "P";
                    }
                    throw new IllegalStateException();
                })
                .collect(Collectors.toMap(s -> s, s -> 1L, Long::sum));
        if (!map.containsKey("S")) {
            map.put("S", 0L);
        }
        if (!map.containsKey("E")) {
            map.put("E", 0L);
        }
        if (!map.containsKey("C")) {
            map.put("C", 0L);
        }
        if (!map.containsKey("P")) {
            map.put("P", 0L);
        }
        return map;
    }

    Map<String, Long> add(Map<String, Long> a, Map<String, Long> b) {
        Map<String, Long> result = new HashMap<>();
        result.put("S", a.get("S") + b.get("S"));
        result.put("E", a.get("E") + b.get("E"));
        result.put("C", a.get("C") + b.get("C"));
        result.put("P", a.get("P") + b.get("P"));
        return result;
    }

    @Disabled
    @Test
    void debugTest() {
        String FEN = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - ";
        Map<String, Long> result = debugPerft(FENParser.getInstance().parseFEN(FEN), 4);
        System.out.println("S" + result.get("S"));
        System.out.println("C" + result.get("C"));
        System.out.println("E" + result.get("E"));
        System.out.println("P" + result.get("P"));
    }

    @Disabled
    @Test
    void divideTest() {
        String FEN = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - ";
        assertEquals(4085603, divide(FENParser.getInstance().parseFEN(FEN), 4, true));
    }
}
