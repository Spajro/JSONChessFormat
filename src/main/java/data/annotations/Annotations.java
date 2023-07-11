package data.annotations;

import java.util.ArrayList;

public class Annotations {
    private String textAnnotation = "";
    private final ArrayList<ArrowAnnotation> arrowAnnotations = new ArrayList<>();
    private final ArrayList<FieldAnnotation> fieldAnnotations = new ArrayList<>();

    public String getTextAnnotation() {
        return textAnnotation;
    }

    public ArrayList<ArrowAnnotation> getArrowAnnotations() {
        return arrowAnnotations;
    }

    public ArrayList<FieldAnnotation> getFieldAnnotations() {
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
