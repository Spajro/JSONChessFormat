package dis;

import ant.Annotation;

public class AnnotationDisplayData extends DisplayData {
    private final Annotation Ant;
    public AnnotationDisplayData(Annotation A){
        Ant=A;
    }
    public Annotation getAnt() {
        return Ant;
    }
}
