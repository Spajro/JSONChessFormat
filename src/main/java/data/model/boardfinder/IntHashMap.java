package data.model.boardfinder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntHashMap<T> implements Map<Integer, T> {
    private final ArrayList<T> map = new ArrayList<>();
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
        int index = (int) key;
        if (index < listSize) {
            return false;
        }
        return map.get(index) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        T list = (T) value;
        return map.stream().anyMatch(list::equals);
    }

    @Override
    public T get(Object key) {
        return map.get((Integer) key);
    }

    @Override
    public T put(Integer key, T value) {
        if (key >= listSize) {
            increaseListSize(key - listSize + 1);
        }

        T actual = map.get(key);
        map.set(key, value);

        if (actual != null) {
            return actual;
        } else {
            size++;
            return null;
        }
    }

    private void increaseListSize(int toIncrease) {
        for (int i = 0; i < toIncrease; i++) {
            map.add(null);
        }
        listSize += toIncrease;
    }

    @Override
    public T remove(Object key) {
        T record = map.get((Integer) key);
        map.set(0, null);
        return record;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends T> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return IntStream.range(0, listSize)
                .filter(i -> map.get(i) != null)
                .boxed()
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<T> values() {
        return IntStream.range(0, listSize)
                .filter(i -> map.get(i) != null)
                .mapToObj(map::get)
                .toList();
    }

    @Override
    public Set<Entry<Integer, T>> entrySet() {
        return IntStream.range(0, listSize)
                .filter(i -> map.get(i) != null)
                .mapToObj(i -> new AbstractMap.SimpleEntry<>(i, map.get(i)))
                .collect(Collectors.toSet());
    }
}
