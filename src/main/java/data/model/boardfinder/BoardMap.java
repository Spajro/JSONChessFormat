package data.model.boardfinder;

import java.util.*;

public class BoardMap implements Map<Integer, ArrayList<BoardRecord>> {
    private final ArrayList<ArrayList<BoardRecord>> map = new ArrayList<>();
    private int size = 0;
    private int listSize = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }//TODO

    @Override
    public boolean containsValue(Object value) {
        return false;
    }//TODO

    @Override
    public ArrayList<BoardRecord> get(Object key) {
        return map.get((Integer) key);
    }

    @Override
    public ArrayList<BoardRecord> put(Integer key, ArrayList<BoardRecord> value) {
        if (key >= listSize) {
            increaseListSize(key - listSize + 1);
        }
        map.set(key, value);
        size++;
        return null;
    }

    private void increaseListSize(int toIncrease) {
        for (int i = 0; i < toIncrease; i++) {
            map.add(null);
        }
        listSize += toIncrease;
    }

    @Override
    public ArrayList<BoardRecord> remove(Object key) {
        ArrayList<BoardRecord> record = map.get((Integer) key);
        map.set(0, new ArrayList<>());
        return record;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends ArrayList<BoardRecord>> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return null;//TODO
    }

    @Override
    public Collection<ArrayList<BoardRecord>> values() {
        return null;//TODO
    }

    @Override
    public Set<Entry<Integer, ArrayList<BoardRecord>>> entrySet() {
        return null;//TODO
    }
}
