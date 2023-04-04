package data.json;

import chess.utility.AlgebraicParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        Diagram diagram = parent.makeMove(algebraicParser.longAlgebraicToMove(moveName, parent.getBoard().getColor().swap()), null);
        if (jsonNode.get("moves") != null) {
            jsonNode.get("moves").forEach(node -> from(diagram, node));
        }
    }
}
