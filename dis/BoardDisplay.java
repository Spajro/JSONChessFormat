package dis;

import dts.Diagram;
import hlp.Translator;

public class BoardDisplay implements Display {
    Diagram state;
    Translator Trans;
    public BoardDisplay(){
        state = new Diagram();
        Trans = new Translator();
    }
    @Override
    public void Update(DisplayData DD){
        BoardDisplayData BDD=(BoardDisplayData)DD;
        state=BDD.Diag;
    }
    public void Print(){
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
