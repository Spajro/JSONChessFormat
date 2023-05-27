package data.model.metadata;

public record GameData(
        String event,
        String site,
        String date,
        String round,
        String white,
        String black,
        String result) implements MetaData{
}
