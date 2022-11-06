package data.json;

import java.util.List;

public class ListJsonFactory {
    public static <T extends Jsonable> String listToJson(List<T> list) {
        StringBuilder result = new StringBuilder("[");
        list.forEach(e -> result.append(e.toJson()).append(","));
        result.append("]");
        return result.toString();
    }
}
