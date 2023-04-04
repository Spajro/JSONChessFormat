package data.json;

import data.annotations.Annotations;
import data.annotations.ArrowAnnotation;
import data.annotations.FieldAnnotation;
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
        return "{\"moveName\":\"" +
                diagram.getMoveName() +
                "\",\"moves\":" +
                listJsonFactory.listToJson(diagram.getNextDiagrams(), this::toJson) +
                ",\"annotations\":" +
                toJson(diagram.getAnnotations()) +
                "}";
    }

    private String toJson(Annotations annotations) {
        return "{\"text\":\"" +
                annotations.getTextAnnotation() +
                "\",\"arrows\":" +
                listJsonFactory.listToJson(annotations.getArrowAnnotations(), this::toJson) +
                ",\"fields\":" +
                listJsonFactory.listToJson(annotations.getFieldAnnotations(), this::toJson) +
                "}";
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
                "\"}}";
    }

    private String toJson(FieldAnnotation field) {
        return "{\"x\":\"" + field.getX() + "\",\"y\":\"" + field.getY() + "\"}";
    }
}
