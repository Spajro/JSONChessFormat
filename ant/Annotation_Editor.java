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
        //TODO
    }

    @Override
    public AnnotationDisplayData Display() {
        return new AnnotationDisplayData(Ant);
    }

    @Override
    public void Exit() {
//TODO
    }


    // edycja i dodawanie annotacji
}
