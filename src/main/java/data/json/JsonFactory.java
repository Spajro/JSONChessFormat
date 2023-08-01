package data.json;

import chess.moves.raw.RawMove;
import chess.utility.AlgebraicUtility;
import chess.utility.RawAlgebraicFactory;
import data.annotations.Annotations;
import data.annotations.ArrowAnnotation;
import data.annotations.FieldAnnotation;
import data.annotations.GraphicAnnotation;
import data.model.DataModel;
import data.model.Diagram;
import data.model.metadata.GameData;
import log.Log;

public class JsonFactory {
    private final DataModel dataModel;
    private final ListJsonFactory listJsonFactory = new ListJsonFactory();
    private final AlgebraicUtility algebraicUtility = AlgebraicUtility.getInstance();
    private final RawAlgebraicFactory rawAlgebraicFactory = new RawAlgebraicFactory();

    public JsonFactory(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public String toJson() {
        long startTime = System.nanoTime();
        String json = "{\"root\":" + toJson(dataModel.getActualNode().getRoot()) + "}";
        long endTime = System.nanoTime();
        long nanoDuration = (endTime - startTime);
        double secondDuration = ((double) nanoDuration / Math.pow(10, 9));
        double nodesPerSec = dataModel.getGames().size() / secondDuration;
        Log.debug("Preparing json time: " + secondDuration + "s with speed: " + nodesPerSec + "gps"); //TODO FOR DEBUG
        return json;
    }

    private String toJson(Diagram diagram) {
        StringBuilder result = new StringBuilder();
        result.append('{')
                .append("\"moveName\":")
                .append(diagram
                        .getCreatingMove()
                        .map(this::toJson)
                        .orElse("\"" + diagram.getMoveName() + "\"")
                )
                .append(",");
        if (!diagram.getMetaData().isEmpty()) {
            result.append("\"metadata\":")
                    .append(listJsonFactory.listToJson(diagram.getGameData(), this::toJson))
                    .append(',');
        }
        if (diagram.isLazy()) {
            result.append("\"movesList\":")
                    .append(listJsonFactory.listToJson(diagram.getLazyMoves(), this::toJson))
                    .append(',');
        } else if (!diagram.getNextDiagrams().isEmpty()) {
            result.append("\"moves\":")
                    .append(listJsonFactory.listToJson(diagram.getNextDiagrams(), this::toJson))
                    .append(',');
        }
        if (!diagram.getAnnotations().isEmpty()) {
            result.append("\"annotations\":")
                    .append(toJson(diagram.getAnnotations()))
                    .append(',');
        }
        result.deleteCharAt(result.length() - 1);
        result.append('}');
        return result.toString();
    }

    private String toJson(RawMove rawMove) {
        return "\"" + rawAlgebraicFactory.moveToRawAlgebraic(rawMove) + "\"";
    }

    private String toJson(GameData metaData) {
        return '{' +
                "\"event\":" +
                metaData.event() +
                "," +
                "\"site\":" +
                metaData.site() +
                "," +
                "\"date\":" +
                metaData.date() +
                "," +
                "\"round\":" +
                metaData.round() +
                "," +
                "\"white\":" +
                metaData.white() +
                "," +
                "\"black\":" +
                metaData.black() +
                "," +
                "\"result\":" +
                metaData.result() +
                "," +
                "\"length\":" +
                metaData.length() +
                '}';
    }

    private String toJson(Annotations annotations) {
        StringBuilder result = new StringBuilder();
        result.append('{');
        if (!annotations.getTextAnnotation().isEmpty()) {
            result.append("\"text\":\"")
                    .append(annotations.getTextAnnotation())
                    .append("\",");
        }
        if (!annotations.getArrowAnnotations().isEmpty()) {
            result.append("\"arrows\":")
                    .append(listJsonFactory.listToJson(annotations.getArrowAnnotations(), this::toJson))
                    .append(",");
        }
        if (!annotations.getFieldAnnotations().isEmpty()) {
            result.append("\"fields\":")
                    .append(listJsonFactory.listToJson(annotations.getFieldAnnotations(), this::toJson))
                    .append(',');
        }
        result.deleteCharAt(result.length() - 1);
        result.append('}');
        return result.toString();
    }

    private String toJson(ArrowAnnotation arrow) {
        return "{" +
                "\"arrow\":\"" +
                algebraicUtility.positionToAlgebraic(arrow.getStartPosition()) +
                algebraicUtility.positionToAlgebraic(arrow.getEndPosition()) +
                "\",\"color\":\"" +
                GraphicAnnotation.drawColorToString(arrow.getColor()) +
                "\"}";
    }

    private String toJson(FieldAnnotation field) {
        return "{\"position\": \"" +
                algebraicUtility.positionToAlgebraic(field) +
                "\",\"color\":\"" +
                GraphicAnnotation.drawColorToString(field.getColor()) +
                "\"}";
    }
}
