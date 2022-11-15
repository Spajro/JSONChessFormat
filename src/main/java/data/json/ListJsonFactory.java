package data.json;

import java.util.List;

public class ListJsonFactory {
    public static <T extends Jsonable> String listToJson(List<T> list) {
        StringBuilder result = new StringBuilder("[");
        list.forEach(e -> result.append(e.toJson()).append(","));
        if (list.size() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        result.append("]");
        return result.toString();
    }
}
