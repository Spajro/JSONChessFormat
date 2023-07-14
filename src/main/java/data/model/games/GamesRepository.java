package data.model.games;

import data.model.Diagram;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;

import java.util.*;

public class GamesRepository {
    private Map<MetaData, Diagram> games = new HashMap<>();

    public void setNewTree(Diagram root) {
        games = gatherMetadataFromTree(root);
    }

    private Map<MetaData, Diagram> gatherMetadataFromTree(Diagram root) {
        Map<MetaData, Diagram> result = new HashMap<>();
        Stack<Diagram> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Diagram node = stack.pop();
            if (!node.getMetaData().isEmpty()) {
                node.getMetaData().forEach(metaData -> result.put(metaData, node));
            } else {
                stack.addAll(node.getNextDiagrams());
            }
        }
        return result;
    }

    public void put(MetaData metaData, Diagram diagram) {
        games.put(metaData, diagram);
    }

    public void update(GamesUpdateEvent event) {
        games.putAll(event.gamesMap());
    }

    public Diagram get(MetaData metaData) {
        return games.get(metaData);
    }

    public List<GameData> getGameData() {
        return games.keySet().stream()
                .filter(metaData -> metaData instanceof GameData)
                .map(metaData -> (GameData) metaData)
                .toList();
    }
}
