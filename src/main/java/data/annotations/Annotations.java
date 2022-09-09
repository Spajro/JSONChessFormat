package data.annotations;

import java.io.Serializable;
import java.util.LinkedList;

public class Annotations implements Serializable {
    private String textAnnotation="";
    private final LinkedList<GraphicAnnotation> graphicAnnotations=new LinkedList<>();
    private final LinkedList<SignAnnotation> signAnnotations=new LinkedList<>();

    public String getTextAnnotation() {
        return textAnnotation;
    }

    public LinkedList<GraphicAnnotation> getGraphicAnnotations() {
        return graphicAnnotations;
    }

    public LinkedList<SignAnnotation> getSignAnnotations() {
        return signAnnotations;
    }

    public void setTextAnnotation(String textAnnotation) {
        this.textAnnotation = textAnnotation;
    }

    public void addGraphicAnnotation(GraphicAnnotation graphicAnnotation){
        graphicAnnotations.add(graphicAnnotation);
    }

    public void removeGraphicAnnotation(GraphicAnnotation graphicAnnotation){
        graphicAnnotations.remove(graphicAnnotation);
    }

    public void addSignAnnotation(SignAnnotation signAnnotation){
        signAnnotations.add(signAnnotation);
    }

    public void removeSignAnnotation(SignAnnotation signAnnotation){
        signAnnotations.remove(signAnnotation);
    }
}
