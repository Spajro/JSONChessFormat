package data.model;

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

    public void put(MetaData metaData,Diagram diagram){
        games.put(metaData,diagram);
    }

    public Diagram get(MetaData metaData){
        return games.get(metaData);
    }

    public Set<MetaData> getMetadata(){
        return games.keySet();
    }
}
