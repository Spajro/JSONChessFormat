package ant;

import dis.AnnotationDisplayData;
import cmdLine.actionData;
import prg.Mode;

import java.io.Serializable;

public class Annotation_Editor implements Mode, Serializable {
    Annotation Ant;
    public Annotation_Editor(){
    Ant=new Annotation();
    }
    public Annotation_Editor(Annotation A){
        Ant=A;
    }
    @Override
    public void Make_action(actionData AD) {
        switch (AD.getCode()) {
        case "ANT" -> AddText((String)AD.getParam());
        case "ADT" -> DeleteText();
            default -> System.out.print("Annotation editor corrupted code");
        }
    }

    @Override
    public AnnotationDisplayData Display() {
        return new AnnotationDisplayData(Ant);
    }

    @Override
    public void Exit() {
//TODO
    }

    void AddText(String S){
        Ant.setText(S);
    }
    void DeleteText(){
        Ant.setText(null);
    }

    // edycja i dodawanie annotacji
}
