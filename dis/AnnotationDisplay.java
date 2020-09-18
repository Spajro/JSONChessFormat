package dis;

import ant.Annotation;

public class AnnotationDisplay implements Display {
    Annotation A;
    AnnotationDisplay(){
        A=new Annotation();
    }
    @Override
    public void Update(DisplayData DD) {
        AnnotationDisplayData ADD=(AnnotationDisplayData)DD;
        A=ADD.getAnt();
    }

    @Override
    public void Print() {
        if(A.getText()!=null)System.out.print(A.getText());
    }
}
