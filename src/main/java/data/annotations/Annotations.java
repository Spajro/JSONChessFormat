package data.annotations;

import data.json.Jsonable;
import data.json.ListJsonFactory;

import java.util.LinkedList;

public class Annotations implements Jsonable {
    private String textAnnotation = "";
    private final LinkedList<ArrowAnnotation> arrowAnnotations = new LinkedList<>();
    private final LinkedList<FieldAnnotation> fieldAnnotations = new LinkedList<>();

    public String getTextAnnotation() {
        return textAnnotation;
    }

    public LinkedList<ArrowAnnotation> getArrowAnnotations() {
        return arrowAnnotations;
    }

    public LinkedList<FieldAnnotation> getFieldAnnotations() {
        return fieldAnnotations;
    }


    public void setTextAnnotation(String textAnnotation) {
        this.textAnnotation = textAnnotation;
    }

    @Override
    public String toJson() {
        return "{\"text\":\"" +
                textAnnotation +
                "\",\"arrows\":" +
                ListJsonFactory.listToJson(arrowAnnotations) +
                ",\"fields\":" +
                ListJsonFactory.listToJson(fieldAnnotations) +
                "}";
    }
}
