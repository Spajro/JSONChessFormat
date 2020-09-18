package ant;

import dis.AnnotationDisplayData;
import prg.Action_data;
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
    public void Make_action(Action_data AD) {
        switch (AD.Get_code()) {
        case "ANT" -> AddText((String)AD.Get_param());
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
