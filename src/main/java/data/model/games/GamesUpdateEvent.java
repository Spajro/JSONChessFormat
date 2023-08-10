package data.model.games;

import data.model.Diagram;
import data.model.metadata.MetaData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record GamesUpdateEvent(Map<MetaData, Diagram> gamesMap) {
    public GamesUpdateEvent join(GamesUpdateEvent gamesUpdateEvent) {
        Map<MetaData, Diagram> result = new java.util.HashMap<>(Map.copyOf(gamesMap));
        result.putAll(gamesUpdateEvent.gamesMap());
        return new GamesUpdateEvent(result);
    }

    public static GamesUpdateEvent of(List<MetaData> list, Diagram diagram) {
        Map<MetaData, Diagram> result = new HashMap<>();
        list.forEach(metaData -> result.put(metaData, diagram));
        return new GamesUpdateEvent(result);
    }

    public static GamesUpdateEvent of(MetaData metadata, Diagram diagram) {
        Map<MetaData, Diagram> result = new HashMap<>();
        result.put(metadata, diagram);
        return new GamesUpdateEvent(result);
    }

    public static GamesUpdateEvent empty() {
        return new GamesUpdateEvent(Map.of());
    }
}
