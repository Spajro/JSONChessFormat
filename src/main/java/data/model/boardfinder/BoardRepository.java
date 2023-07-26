package data.model.boardfinder;

import chess.board.lowlevel.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardRepository {
    private final Map<Integer, ArrayList<BoardRecord>> map = new IntHashMap<>();

    public void put(Board board, BoardRecord boardRecord) {
        int hash = board.hashCode() % 100000;
        if (hash < 0) {
            hash = hash * -1;
        }
        if (map.containsKey(hash)) {
            map.get(hash).add(boardRecord);
        } else {
            map.put(hash, new ArrayList<>(List.of(boardRecord)));
        }
    }

    public List<BoardRecord> get(Board board) {
        int hash = board.hashCode();
        return map.get(hash);
    }

}
