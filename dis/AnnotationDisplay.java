package dis;

import ant.Annotation;

public class AnnotationDisplay implements Display {
    Annotation A;
    @Override
    public void Update(DisplayData DD) {
        AnnotationDisplayData ADD=(AnnotationDisplayData)DD;
        A=ADD.getAnt();
    }

    @Override
    public void Print() {
        System.out.print(A.getText());
    }
}
