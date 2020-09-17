package hlp;

import dts.Diagram;
import prg.BoardDisplayData;

public class TextDisplay {
    Diagram state;
    Translator Trans;
    public TextDisplay(){
        state = new Diagram();
        Trans = new Translator();
    }
    public void Update(BoardDisplayData D){
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
