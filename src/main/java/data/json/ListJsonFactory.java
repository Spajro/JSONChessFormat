package data.json;

import java.util.List;
import java.util.function.Function;

class ListJsonFactory {
    public <T> String listToJson(List<T> list, Function<T, String> toJson) {
        StringBuilder result = new StringBuilder("[");
        list.forEach(e -> result.append(toJson.apply(e)).append(','));
        if (!list.isEmpty()) {
            result.deleteCharAt(result.length() - 1);
        }
        result.append("]");
        return result.toString();
    }
}
