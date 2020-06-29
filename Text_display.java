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
                int temp=state.T.get(j,7-i);
                if(temp==0){
                    System.out.print("00");
                }
                else {
                    System.out.print(temp);
                }
                System.out.print(" ");
            }
            System.out.println();
            System.out.println();
        }
    }
}
