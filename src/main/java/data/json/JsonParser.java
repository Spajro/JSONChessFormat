package data.json;

import chess.Position;
import chess.moves.RawMove;
import chess.utility.AlgebraicParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.annotations.Annotations;
import data.annotations.ArrowAnnotation;
import data.annotations.FieldAnnotation;
import data.annotations.GraphicAnnotation;
import data.model.Diagram;

public class JsonParser {
    ObjectMapper mapper = new ObjectMapper();
    AlgebraicParser algebraicParser = AlgebraicParser.getInstance();

    public Diagram parseJson(String json) {
        try {
            JsonNode root = mapper.readTree(json).get("root");
            String moveName = root.get("moveName").asText();
            if (moveName.equals("Start")) {
                Diagram diagram = new Diagram();
                root.get("moves").forEach(node -> from(diagram, node));
                return diagram;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("json not correct:" + json);
    }

    private void from(Diagram parent, JsonNode jsonNode) {
        String moveName = jsonNode.get("moveName").asText();
        Diagram diagram = parent.makeMove(algebraicParser.parseLongAlgebraic(moveName, parent.getBoard().getColor().swap()), null);
        if (jsonNode.get("moves") != null) {
            jsonNode.get("moves").forEach(node -> from(diagram, node));
        }
        if (jsonNode.get("annotations") != null) {
            parseAnnotations(diagram, jsonNode.get("annotations"));
        }
    }

    private void parseAnnotations(Diagram diagram, JsonNode jsonNode) {
        Annotations annotations = diagram.getAnnotations();
        if (jsonNode.get("text") != null) {
            annotations.setTextAnnotation(jsonNode.get("text").asText());
        }
        if (jsonNode.get("arrows") != null) {
            jsonNode.get("arrows").forEach(node -> annotations.getArrowAnnotations().add(toArrow(node)));
        }
        if (jsonNode.get("fields") != null) {
            jsonNode.get("fields").forEach(node -> annotations.getFieldAnnotations().add(toField(node)));
        }
    }

    private ArrowAnnotation toArrow(JsonNode jsonNode) {
        if (jsonNode.get("start") != null && jsonNode.get("end") != null && jsonNode.get("color") != null) {
            return new ArrowAnnotation(
                    new RawMove(
                            toPosition(jsonNode.get("start")),
                            toPosition(jsonNode.get("end"))),
                    toDrawColor(jsonNode.get("color")));
        }
        throw new IllegalArgumentException("Not a json arrow: " + jsonNode);
    }

    private FieldAnnotation toField(JsonNode jsonNode) {
        if (jsonNode.get("position") != null && jsonNode.get("color") != null) {
            return new FieldAnnotation(
                    toPosition(jsonNode.get("position")),
                    toDrawColor(jsonNode.get("color")));
        }
        throw new IllegalArgumentException("Not a json field annotation: " + jsonNode);
    }

    private Position toPosition(JsonNode jsonNode) {
        if (jsonNode.get("x") != null && jsonNode.get("y") != null) {
            return new Position(
                    jsonNode.get("x").asInt(),
                    jsonNode.get("y").asInt());
        }
        throw new IllegalArgumentException("Not a json position: " + jsonNode);
    }

    private GraphicAnnotation.DrawColor toDrawColor(JsonNode jsonNode) {
        return switch (jsonNode.toString()) {
            case "\"blue\"" -> GraphicAnnotation.DrawColor.BLUE;
            case "\"red\"" -> GraphicAnnotation.DrawColor.RED;
            case "\"yellow\"" -> GraphicAnnotation.DrawColor.YELLOW;
            case "\"green\"" -> GraphicAnnotation.DrawColor.GREEN;
            default -> throw new IllegalStateException("Unexpected value: " + jsonNode);
        };
    }
}
