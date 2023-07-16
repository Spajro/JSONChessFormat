package data.json;

import chess.moves.raw.RawMove;
import chess.moves.valid.executable.ExecutableMove;
import chess.utility.AlgebraicUtility;
import chess.utility.LongAlgebraicParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.MoveParser;
import data.annotations.Annotations;
import data.annotations.ArrowAnnotation;
import data.annotations.FieldAnnotation;
import data.annotations.GraphicAnnotation;
import data.model.Diagram;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;
import log.Log;

import java.util.*;

public class JsonParser {
    private final ObjectMapper mapper = new ObjectMapper();
    private final LongAlgebraicParser longAlgebraicParser = new LongAlgebraicParser();
    private final AlgebraicUtility algebraicUtility = AlgebraicUtility.getInstance();
    private final MoveParser moveParser = MoveParser.getInstance();

    public Diagram parseJson(String json) {
        try {
            JsonNode root = mapper.readTree(json).get("root");
            String moveName = root.get("moveName").asText();
            if (moveName.equals("Root")) {
                Diagram diagram = new Diagram();
                if (root.get("moves") != null) {
                    root.get("moves").forEach(node -> from(diagram, node));
                } else if (root.get("movesList") != null) {
                    fromList(diagram, root.get("movesList"));
                }
                return diagram;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("json not correct:" + json);
    }

    private void from(Diagram parent, JsonNode jsonNode) {
        String moveName = jsonNode.get("moveName").asText();
        Diagram diagram = parent.makeMove(longAlgebraicParser.parseLongAlgebraic(moveName, parent.getBoard().getColor().swap()), null);
        if (jsonNode.get("moves") != null) {
            jsonNode.get("moves").forEach(node -> from(diagram, node));
        } else if (jsonNode.get("movesList") != null) {
            fromList(diagram, jsonNode.get("movesList"));
        }
        if (jsonNode.get("annotations") != null) {
            parseAnnotations(diagram, jsonNode.get("annotations"));
        }
        if (jsonNode.get("metadata") != null) {
            parseMetadata(diagram, jsonNode);
        }
    }

    private void fromList(Diagram diagram, JsonNode movesList) {
        List<String> moves = new ArrayList<>();
        Iterator<JsonNode> iterator = movesList.elements();
        while (iterator.hasNext()) {
            moves.add(iterator.next().asText());
            iterator.remove();
        }
        Optional<ArrayDeque<ExecutableMove>> executableMoves = moveParser.parseMoves(
                diagram.getBoard(),
                moves,
                (move, chessBoard) -> longAlgebraicParser.parseLongAlgebraic(move, chessBoard.getColor()));
        if (executableMoves.isPresent()) {
            ExecutableMove executableMove=executableMoves.get().poll();
            diagram.getNextDiagrams().add(new Diagram(
                    executableMove,
                    diagram.getBoard().makeMove(executableMove),
                    diagram,
                    new ArrayDeque<>(executableMoves.get().stream()
                            .map(ExecutableMove::getRepresentation)
                            .toList()),
                    null//TODO
            ));
        } else {
            Log.log().fail("Missing optional in JsonParser.fromList");
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
        if (jsonNode.get("arrow") != null && jsonNode.get("color") != null) {
            String arrow = jsonNode.get("arrow").asText();
            return new ArrowAnnotation(
                    RawMove.of(
                            algebraicUtility.algebraicToPosition(arrow.substring(0, 2)).orElseThrow(),
                            algebraicUtility.algebraicToPosition(arrow.substring(2)).orElseThrow()),
                    toDrawColor(jsonNode.get("color")));
        }
        throw new IllegalArgumentException("Not a json arrow: " + jsonNode);
    }

    private FieldAnnotation toField(JsonNode jsonNode) {
        if (jsonNode.get("position") != null && jsonNode.get("color") != null) {
            return new FieldAnnotation(
                    algebraicUtility.algebraicToPosition(jsonNode.get("position").asText()).orElseThrow(),
                    toDrawColor(jsonNode.get("color")));
        }
        throw new IllegalArgumentException("Not a json field annotation: " + jsonNode);
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

    private void parseMetadata(Diagram diagram, JsonNode jsonNode) {
        jsonNode.get("metadata").forEach(node -> diagram.addMetadata(toMetadata(node)));
    }

    private MetaData toMetadata(JsonNode jsonNode) {
        return new GameData(
                jsonNode.get("event").asText(),
                jsonNode.get("site").asText(),
                jsonNode.get("date").asText(),
                jsonNode.get("round").asText(),
                jsonNode.get("white").asText(),
                jsonNode.get("black").asText(),
                jsonNode.get("result").asText(),
                jsonNode.get("length").asInt()
        );
    }
}
