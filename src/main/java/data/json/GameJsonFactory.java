package data.json;


import chess.moves.raw.RawMove;
import data.annotations.Annotations;
import data.json.factories.AnnotationsJsonFactory;
import data.json.factories.GameDataJsonFactory;
import data.json.factories.JsonFactory;
import data.json.factories.RawMoveJsonFactory;
import data.model.Diagram;
import data.model.metadata.GameData;

import java.util.ArrayList;

public class GameJsonFactory {
    private final ListJsonFactory listJsonFactory = new ListJsonFactory();
    private final JsonFactory<GameData> gameDataJsonFactory = new GameDataJsonFactory();
    private final JsonFactory<RawMove> rawMoveJsonFactory = new RawMoveJsonFactory();
    private final JsonFactory<Annotations> annotationsJsonFactory = new AnnotationsJsonFactory();

    private record Node(RawMove rawMove, Annotations annotations) {
    }

    public String gameToJson(Diagram diagram, GameData gameData) {
        StringBuilder result = new StringBuilder("{");
        result.append("\"metadata\":")
                .append(gameDataJsonFactory.toJson(gameData))
                .append(',');
        ArrayList<Node> moves = new ArrayList<>();
        diagram.getPathFromRoot().stream()
                .map(diagram1 -> new Node(diagram1.getCreatingMove().orElse(null), diagram1.getAnnotations()))
                .forEachOrdered(moves::add);
        if (diagram.isLazy() && gameData.length() > diagram.depth()) {
            diagram.getLazyMovesList().stream()
                    .map(rawMove -> new Node(rawMove, null))
                    .forEachOrdered(moves::add);
        }
        result.append("\"moves\":")
                .append(listJsonFactory.listToJson(moves, this::toJson))
                .append('}');
        return result.toString();
    }

    private String toJson(Node node) {
        StringBuilder result = new StringBuilder("{");
        result.append("\"move\":")
                .append(rawMoveJsonFactory.toJson(node.rawMove()));
        if (node.annotations() != null && !node.annotations().isEmpty()) {
            result.append(',')
                    .append(annotationsJsonFactory.toJson(node.annotations()));
        }
        result.append("}");
        return result.toString();
    }
}
