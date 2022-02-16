import dis.Displayer;
import prg.Action_data;
import prg.Database_editor;
import hlp.Translator;

import java.util.Scanner;

public class TextMain {

    public static void main(String[] args){
        //Kontroler Cont = new Kontroler();
        Scanner Scan= new Scanner(System.in);
        boolean working = true;
        Displayer Dis= new Displayer();
        //Action_data First = new Action_data("SEL",new Action_data("DTE","TEST"));
        //Cont.Get_cmd(First);

        Database_editor Editor=new Database_editor("Test");
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
                    case "MM" -> Ad = new Action_data("MM", Translator.algebraicToMove(Editor.GetDiag().getBoard(), Scan.nextLine(), Editor.getColor()));
                    case "AN" -> Ad = new Action_data("AN", null);
                    case "QT" -> Ad = new Action_data("QT", null);
                    case "DL" -> Ad = new Action_data("DL",null);
                    case "GB" -> Ad = new Action_data("GB", Integer.parseInt(Scan.nextLine()));
                    case "PM" -> Ad = new Action_data("PM", null);
                    case "PH" -> Ad = new Action_data("PH", null);
                    case "JB" -> Ad = new Action_data("JB", null);
                    case "JF" -> Ad = new Action_data("JF", null);
                    case "Help" -> Ad = new Action_data("HP", null);
                    case "ANT" -> Ad=new Action_data("ANT", Scan.nextLine());
                    case "ADT" -> Ad=new Action_data("ADT", null);
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
                Dis.Update(Editor.GetDiag().getAnnotation().Display());
                Dis.Print();
            }
        }
    }


}
