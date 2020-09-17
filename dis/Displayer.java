package dis;

public class Displayer implements Display {
    AnnotationDisplay AD;
    BoardDisplay BD;
    public Displayer(){
        AD= new AnnotationDisplay();
        BD= new BoardDisplay();
    }
    public void Update(DisplayData DD){
        if(DD instanceof BoardDisplayData){
            BD.Update(DD);
        }
        else if(DD instanceof AnnotationDisplayData){
            AD.Update(DD);
        }
        else{
            System.out.print("Displayer fail");
        }
    }

    @Override
    public void Print() {
        BD.Print();
        AD.Print();
    }
}
