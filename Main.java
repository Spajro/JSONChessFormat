import prg.Action_data;
import prg.Kontroler;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Kontroler Cont = new Kontroler();
        Scanner Scan= new Scanner(System.in);
        boolean working = true;
        Object[] T1 = new Object[1];
        T1[0]="TEST";
        Action_data Start = new Action_data("DTE",T1);
        Object[] T2=new Object[1];
        T2[0]=Start;
        Action_data First = new Action_data("SEL",T2);
        Cont.Get_cmd(First);
        while (working){
            String cmd= Scan.nextLine();
            if(cmd.equals("Q")){
                working=false;
            }
            else{

            }
        }
    }
}
