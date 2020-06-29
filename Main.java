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
        Text_display Dis = new Text_display();
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
                        Ad=new Action_data("MM",Trans.Algebraic_to_move(Cont.GetDiag().T,Scan.nextLine(),Cont.GetColor()));
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

                if (Ad != null) {
                    Cont.Get_cmd(Ad);
                }
                else{
                    System.out.print("Cmd not found -> not executed");
                }
                Dis.Update(Cont.Display());
                Dis.Print_Pos();
            }
        }
    }


}
