import cli.ActionData;
import cli.CommandLineHandler;
import chess.utility.AlgebraicTranslator;

import java.util.Scanner;

public class CliMain {

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
                ActionData data=null;
                switch (cmd) {
                    case "LD" -> data = new ActionData("LD", scanner.nextLine());
                    case "SV" -> data = new ActionData("SV", null);
                    case "MM" -> data = new ActionData("MM", AlgebraicTranslator.longAlgebraicToMove(scanner.nextLine(), handler.getColor()));
                    case "AN" -> data = new ActionData("AN", null);
                    case "QT" -> data = new ActionData("QT", null);
                    case "DL" -> data = new ActionData("DL",null);
                    case "GB" -> data = new ActionData("GB", Integer.parseInt(scanner.nextLine()));
                    case "PM" -> data = new ActionData("PM", null);
                    case "PH" -> data = new ActionData("PH", null);
                    case "JB" -> data = new ActionData("JB", null);
                    case "JF" -> data = new ActionData("JF", null);
                    case "Help" -> data = new ActionData("HP", null);
                    case "ANT" -> data=new ActionData("ANT", scanner.nextLine());
                    case "ADT" -> data=new ActionData("ADT", null);
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
