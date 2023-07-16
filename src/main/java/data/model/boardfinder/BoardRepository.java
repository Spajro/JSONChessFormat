package data.model.boardfinder;

import chess.board.lowlevel.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoardRepository {
    private final HashMap<Integer, List<BoardRecord>> map = new HashMap<>();

    public void put(Board board, BoardRecord boardRecord) {
        int hash = board.hashCode();
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
