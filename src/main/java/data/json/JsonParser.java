package data.json;

import chess.moves.raw.RawMove;
import chess.formats.algebraic.AlgebraicUtility;
import chess.formats.algebraic.RawAlgebraicParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.annotations.Annotations;
import data.annotations.ArrowAnnotation;
import data.annotations.FieldAnnotation;
import data.annotations.GraphicAnnotation;
import data.json.pojo.*;
import data.model.diagrams.Diagram;
import data.model.DiagramController;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;

import java.util.*;

public class JsonParser {
    private final ObjectMapper mapper = new ObjectMapper();
    private final RawAlgebraicParser rawAlgebraicParser = new RawAlgebraicParser();
    private final AlgebraicUtility algebraicUtility = AlgebraicUtility.getInstance();
    private final DiagramController diagramController = new DiagramController();

    private record ToParse(Diagram parent, Node node) {
    }

    public Diagram parseJson(String json) {
        Node jsonNode = parseJsonToPojo(json);
        Diagram root = new Diagram();
        migrate(root, jsonNode);

        if (jsonNode.moves == null) {
            return root;
        }

        ArrayDeque<ToParse> queue = new ArrayDeque<>();
        jsonNode.moves.forEach(node -> queue.add(new ToParse(root, node)));

        while (!queue.isEmpty()) {
            ToParse toParse = queue.pop();
            Diagram diagram = diagramController.makeMove(toParse.parent(), rawAlgebraicParser.rawAlgebraicToMoves(toParse.node().moveName), null).diagram();
            migrate(diagram, toParse.node);
            if (toParse.node.moves != null) {
                toParse.node.moves.forEach(node -> queue.add(new ToParse(diagram, node)));
            }
        }
        return root;
    }

    private Node parseJsonToPojo(String json) {
        try {
            return mapper.readValue(json, Node.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void migrate(Diagram diagram, Node jsonNode) {
        if (jsonNode.movesList != null) {
            fromList(diagram, jsonNode.movesList);
        }
        if (jsonNode.jsonAnnotations != null) {
            parseAnnotations(diagram, jsonNode.jsonAnnotations);
        }
        if (jsonNode.metadata != null) {
            parseMetadata(diagram, jsonNode.metadata);
        }
    }

    private void fromList(Diagram diagram, List<String> movesList) {
        ArrayDeque<RawMove> rawMoves = new ArrayDeque<>();
        movesList.stream()
                .map(rawAlgebraicParser::rawAlgebraicToMoves)
                .forEachOrdered(rawMoves::add);

        diagram.setLazyMoves(rawMoves);
    }


    private void parseAnnotations(Diagram diagram, JsonAnnotations jsonNode) {
        Annotations annotations = diagram.getAnnotations();
        if (jsonNode.text != null) {
            annotations.setTextAnnotation(jsonNode.text);
        }
        if (jsonNode.arrows != null) {
            jsonNode.arrows.forEach(node -> annotations.getArrowAnnotations().add(toArrow(node)));
        }
        if (jsonNode.fields != null) {
            jsonNode.fields.forEach(node -> annotations.getFieldAnnotations().add(toField(node)));
        }
    }

    private ArrowAnnotation toArrow(JsonArrow jsonNode) {
        if (jsonNode.arrow != null && jsonNode.color != null) {
            String arrow = jsonNode.arrow;
            return new ArrowAnnotation(
                    RawMove.of(
                            algebraicUtility.algebraicToPosition(arrow.substring(0, 2)).orElseThrow(),
                            algebraicUtility.algebraicToPosition(arrow.substring(2)).orElseThrow()),
                    toDrawColor(jsonNode.color));
        }
        throw new IllegalArgumentException("Not a json arrow: " + jsonNode);
    }

    private FieldAnnotation toField(JsonField jsonNode) {
        if (jsonNode.position != null && jsonNode.color != null) {
            return new FieldAnnotation(
                    algebraicUtility.algebraicToPosition(jsonNode.position).orElseThrow(),
                    toDrawColor(jsonNode.color));
        }
        throw new IllegalArgumentException("Not a json field annotation: " + jsonNode);
    }

    private GraphicAnnotation.DrawColor toDrawColor(String jsonNode) {
        return switch (jsonNode) {
            case "blue" -> GraphicAnnotation.DrawColor.BLUE;
            case "red" -> GraphicAnnotation.DrawColor.RED;
            case "yellow" -> GraphicAnnotation.DrawColor.YELLOW;
            case "green" -> GraphicAnnotation.DrawColor.GREEN;
            default -> throw new IllegalStateException("Unexpected value: " + jsonNode);
        };
    }

    private void parseMetadata(Diagram diagram, List<JsonGameData> jsonNode) {
        jsonNode.forEach(node -> diagram.getMetaData().add(toMetadata(node)));
    }

    private MetaData toMetadata(JsonGameData jsonNode) {
        return new GameData(
                jsonNode.event,
                jsonNode.site,
                jsonNode.date,
                jsonNode.round,
                jsonNode.white,
                jsonNode.black,
                jsonNode.result,
                jsonNode.length
        );
    }
}
