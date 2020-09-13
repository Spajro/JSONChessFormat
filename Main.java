import prg.Action_data;
import prg.Database_editor;
import prg.Kontroler;
import hlp.Translator;
import prg.Mode;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        //Kontroler Cont = new Kontroler();
        Scanner Scan= new Scanner(System.in);
        boolean working = true;
        Translator Trans = new Translator();
        Text_display Dis = new Text_display();
        //Action_data First = new Action_data("SEL",new Action_data("DTE","TEST"));
        //Cont.Get_cmd(First);

        Mode Editor=new Database_editor("Test");
        while (working){
            String cmd= Scan.nextLine();
            if(cmd.equals("Q")){
                working=false;
            }
            else{
                Action_data Ad=null;
                switch (cmd) {
                    case "LD" -> Ad = new Action_data("LD", Scan.nextLine());
                    case "SV" -> Ad = new Action_data("SV", null);
                    case "MM" -> Ad = new Action_data("MM", Trans.Algebraic_to_move(Editor.GetDiag().T, Scan.nextLine(), Editor.GetColor()));
                    case "AN" -> Ad = new Action_data("AN", null);
                    case "QT" -> Ad = new Action_data("QT", null);
                    case "DLA" -> Ad = new Action_data("DL", Editor.GetDiag());
                    case "GB" -> Ad = new Action_data("GB", Integer.parseInt(Scan.nextLine()));
                    default -> System.out.print("Unknown code MAIN");
                }

                if (Ad != null) {
                    Editor.Make_action(Ad);
                }
                else{
                    System.out.print("Cmd not found -> not executed");
                    System.out.print(cmd);
                }
                Dis.Update(Editor.Display());
                Dis.Print_Pos();
            }
        }
    }


}
