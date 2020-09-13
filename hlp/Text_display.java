package hlp;

import dts.Diagram;
import hlp.Translator;
import prg.Display_data;

public class Text_display {
    Diagram state;
    Translator Trans;
    public Text_display(){
        state = new Diagram();
        Trans = new Translator();
    }
    public void Update(Display_data D){
        state=D.Diag;
    }
    public void Print_Pos(){
        System.out.println();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                int temp=state.T.get(j,7-i);
                if(temp==0){
                    System.out.print("00");
                }
                else {
                    System.out.print(Trans.Num_to_Fig(temp));
                }
                System.out.print(" ");
            }
            System.out.println();
            System.out.println();
        }
    }
}
