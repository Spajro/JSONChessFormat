import cmdLine.actionData;
import cmdLine.CommandLineHandler;
import hlp.Translator;

import java.util.Scanner;

public class TextMain {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        boolean working = true;

        CommandLineHandler handler=new CommandLineHandler();
        while (working){
            String cmd= scanner.nextLine();
            if(cmd.equals("Q")){
                working=false;
            }
            else{
                actionData data=null;
                switch (cmd) {
                    case "LD" -> data = new actionData("LD", scanner.nextLine());
                    case "SV" -> data = new actionData("SV", null);
                    case "MM" -> data = new actionData("MM", Translator.algebraicToMove(handler.getDiag().getBoard(), scanner.nextLine(), handler.getcolor()));
                    case "AN" -> data = new actionData("AN", null);
                    case "QT" -> data = new actionData("QT", null);
                    case "DL" -> data = new actionData("DL",null);
                    case "GB" -> data = new actionData("GB", Integer.parseInt(scanner.nextLine()));
                    case "PM" -> data = new actionData("PM", null);
                    case "PH" -> data = new actionData("PH", null);
                    case "JB" -> data = new actionData("JB", null);
                    case "JF" -> data = new actionData("JF", null);
                    case "Help" -> data = new actionData("HP", null);
                    case "ANT" -> data=new actionData("ANT", scanner.nextLine());
                    case "ADT" -> data=new actionData("ADT", null);
                    default -> System.out.print("Unknown code MAIN");
                }

                if (data != null) {
                    handler.makeAction(data);
                }
                else{
                    System.out.print("Cmd not found -> not executed");
                    System.out.print(cmd);
                }
                handler.display();
            }
        }
    }
}
