package data.annotations;

import data.Jsonable;
import data.ListJsonFactory;

import java.util.LinkedList;

public class Annotations implements Jsonable {
    private String textAnnotation = "";
    private final LinkedList<ArrowAnnotation> arrowAnnotations = new LinkedList<>();
    private final LinkedList<FieldAnnotation> fieldAnnotations = new LinkedList<>();
    private final LinkedList<SignAnnotation> signAnnotations = new LinkedList<>();

    public String getTextAnnotation() {
        return textAnnotation;
    }

    public LinkedList<ArrowAnnotation> getArrowAnnotations() {
        return arrowAnnotations;
    }

    public LinkedList<FieldAnnotation> getFieldAnnotations() {
        return fieldAnnotations;
    }

    public LinkedList<SignAnnotation> getSignAnnotations() {
        return signAnnotations;
    }

    public void setTextAnnotation(String textAnnotation) {
        this.textAnnotation = textAnnotation;
    }

    public void addFieldAnnotation(FieldAnnotation fieldAnnotation) {
        fieldAnnotations.add(fieldAnnotation);
    }

    public void removeFieldAnnotation(FieldAnnotation fieldAnnotation) {
        fieldAnnotations.remove(fieldAnnotation);
    }

    public void addArrowAnnotation(ArrowAnnotation arrowAnnotation) {
        arrowAnnotations.add(arrowAnnotation);
    }

    public void removeArrowAnnotation(ArrowAnnotation arrowAnnotation) {
        arrowAnnotations.remove(arrowAnnotation);
    }

    public void addSignAnnotation(SignAnnotation signAnnotation) {
        signAnnotations.add(signAnnotation);
    }

    public void removeSignAnnotation(SignAnnotation signAnnotation) {
        signAnnotations.remove(signAnnotation);
    }

    @Override
    public String toJson() {
        return "{\"text\":\"" +
                textAnnotation +
                "\",\"arrows\":" +
                ListJsonFactory.listToJson(arrowAnnotations) +
                ",\"fields\":" +
                ListJsonFactory.listToJson(fieldAnnotations) +
                ",\"signs\":" +
                ListJsonFactory.listToJson(signAnnotations) +
                "}";
    }
}
