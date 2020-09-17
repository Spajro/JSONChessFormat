package ant;

import prg.DisplayData;

public class AnnotationDisplayData extends DisplayData {
    private final Annotation Ant;
    AnnotationDisplayData(Annotation A){
        Ant=A;
    }
    public Annotation getAnt() {
        return Ant;
    }
}
