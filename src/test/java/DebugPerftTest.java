import chess.board.ChessBoard;
import chess.moves.*;
import chess.utility.FENTranslator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DebugPerftTest {
    Map<String, Long> debugPerft(ChessBoard board, int n) {
        if (n == 1) {
            return listToMoveTypesMap(board.getGenerator().getAllPossibleExecutableMoves());
        } else {
            Map<String, Long> result=Map.of("S",0L,"E",0L,"C",0L,"P",0L);
            List<Map<String, Long>> list= board.getGenerator().getAllPossibleExecutableMoves().stream()
                    .map(move -> debugPerft(board.makeMove(move), n - 1)).toList();
            for (Map<String, Long> map : list) {
                result=add(result,map);
            }
            return result;
        }
    }

    Map<String, Long> listToMoveTypesMap(List<ExecutableMove> moves) {
        Map<String, Long> map= moves.stream()
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
        if(!map.containsKey("S")){
            map.put("S",0L);
        }
        if(!map.containsKey("E")){
            map.put("E",0L);
        }
        if(!map.containsKey("C")){
            map.put("C",0L);
        }
        if(!map.containsKey("P")){
            map.put("P",0L);
        }
        return map;
    }
    Map<String, Long> add(Map<String, Long> a,Map<String, Long> b){
        Map<String, Long> result=new HashMap<>();
        result.put("S",a.get("S")+b.get("S"));
        result.put("E",a.get("E")+b.get("E"));
        result.put("C",a.get("C")+b.get("C"));
        result.put("P",a.get("P")+b.get("P"));
        return result;
    }

    @Test
    void test(){
        String FEN="r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - ";
        Map<String,Long> result=debugPerft(new FENTranslator().parseFEN(FEN),4);
        System.out.println("S"+result.get("S"));
        System.out.println("C"+result.get("C"));
        System.out.println("E"+result.get("E"));
        System.out.println("P"+result.get("P"));
    }
}
