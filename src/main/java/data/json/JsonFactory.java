package data.json;

import data.annotations.Annotations;
import data.annotations.ArrowAnnotation;
import data.annotations.FieldAnnotation;
import data.annotations.GraphicAnnotation;
import data.model.DataModel;
import data.model.Diagram;

public class JsonFactory {
    private final DataModel dataModel;
    private final ListJsonFactory listJsonFactory = new ListJsonFactory();

    public JsonFactory(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public String toJson() {
        return "{\"root\":" + toJson(dataModel.getActualNode().getRoot()) + "}";
    }

    private String toJson(Diagram diagram) {
        StringBuilder result = new StringBuilder();
        result.append('{')
                .append("\"moveName\":\"")
                .append(diagram.getMoveName())
                .append("\",");
        if (!diagram.getNextDiagrams().isEmpty()) {
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
                "\"start\":{\"x\":\"" +
                arrow.getStartPosition().getX() +
                "\",\"y\":\"" +
                arrow.getStartPosition().getY() +
                "\"},\"end\":{\"x\":\"" +
                arrow.getEndPosition().getX() +
                "\",\"y\":\"" +
                arrow.getEndPosition().getY() +
                "\"},\"color\":\"" +
                GraphicAnnotation.drawColorToString(arrow.getColor()) +
                "\"}";
    }

    private String toJson(FieldAnnotation field) {
        return "{\"position\": { \"x\":\"" +
                field.getX() +
                "\",\"y\":\"" +
                field.getY() +
                "\"},\"color\":\"" +
                GraphicAnnotation.drawColorToString(field.getColor()) +
                "\"}";
    }
}
