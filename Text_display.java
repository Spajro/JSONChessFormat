import dts.Diagram;
import prg.Display_data;

public class Text_display {
    Diagram state;
    public Text_display(){
        state = new Diagram();
    }
    public void Update(Display_data D){
        state=D.Diag;
    }
    public void Print_Pos(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                System.out.print(state.T.get(i,j));
                System.out.print(" ");
            }
            System.out.println();
            System.out.println();
        }
    }
}
