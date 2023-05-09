package data.annotations;

import java.util.LinkedList;

public class Annotations {
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

    public boolean isEmpty() {
        return textAnnotation.isEmpty() && arrowAnnotations.isEmpty() && fieldAnnotations.isEmpty();
    }

    public void addAll(Annotations annotations) {
        arrowAnnotations.addAll(annotations.getArrowAnnotations());
        fieldAnnotations.addAll(annotations.getFieldAnnotations());
        if (textAnnotation.isEmpty()) {
            textAnnotation = annotations.textAnnotation;
        } else if (!annotations.textAnnotation.isEmpty()) {
            textAnnotation = textAnnotation + " | " + annotations.textAnnotation;
        }
    }
}
