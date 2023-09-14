package data.json.factories;

public interface JsonFactory<T> {

    String toJson(T object);
}
