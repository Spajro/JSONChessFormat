import prg.Action_data;
import prg.Kontroler;
import prg.Translator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Kontroler Cont = new Kontroler();
        Scanner Scan= new Scanner(System.in);
        boolean working = true;
        Translator Trans = new Translator();
        Action_data First = new Action_data("SEL",new Action_data("DTE","TEST"));
        Cont.Get_cmd(First);
        while (working){
            String cmd= Scan.nextLine();
            if(cmd.equals("Q")){
                working=false;
            }
            else{
                Action_data Ad=null;
                switch (cmd){
                    case "LD":
                        Ad=new Action_data("LD",Scan.nextLine());
                        break;
                    case "SV":
                        Ad=new Action_data("SV",null);
                        break;
                    case "MM":
                        Ad=new Action_data("MM",Trans.Unify_to_move(Cont.GetDiag().T,Scan.nextLine()));
                        break;
                    case "AN":
                        Ad=new Action_data("AN",null);
                        break;
                    case "QT":
                        Ad=new Action_data("QT",null);
                        break;
                    case "DLA":
                        Ad=new Action_data("DL",Cont.GetDiag());
                        break;
                    case "GB":
                        Ad=new Action_data("GB",Scan.nextInt());
                        break;
                    default:
                        System.out.print("Unknown code MAIN");
                }

                Cont.Get_cmd(Ad);
            }
        }
    }


}
