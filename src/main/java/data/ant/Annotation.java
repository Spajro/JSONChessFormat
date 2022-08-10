package data.ant;

import java.io.Serializable;
import java.util.LinkedList;

public class Annotation implements Serializable {
    //annotacje do pozycji, zarowno tekstowe, znakowe jak i graficzne, strzalki zaznaczenia itd
    private String Text;
    private LinkedList<GraphicAnnotation> Graf;
    private LinkedList<SignAnnotation> Signs;

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public void AddGraf(GraphicAnnotation A) {
        Graf.add(A);
    }

    public LinkedList<GraphicAnnotation> getGraf() {
        return Graf;
    }

    public LinkedList<SignAnnotation> getSigns() {
        return Signs;
    }

    public void AddSign(SignAnnotation A) {
        Signs.add(A);
    }
}
