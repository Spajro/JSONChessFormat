import hlp.TextDisplay;
import prg.Action_data;
import prg.Database_editor;
import hlp.Translator;
import prg.Mode;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        //Kontroler Cont = new Kontroler();
        Scanner Scan= new Scanner(System.in);
        boolean working = true;
        Translator Trans = new Translator();
        TextDisplay Dis = new TextDisplay();
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
                    case "DL" -> Ad = new Action_data("DL",null);
                    case "GB" -> Ad = new Action_data("GB", Integer.parseInt(Scan.nextLine()));
                    case "PM" -> Ad = new Action_data("PM", null);
                    case "PH" -> Ad = new Action_data("PH", null);
                    case "JB" -> Ad = new Action_data("JB", null);
                    case "JF" -> Ad = new Action_data("JF", null);
                    case "Help" -> Ad = new Action_data("HP", null);
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
