import cli.ActionData;
import cli.CommandLineHandler;
import chess.utility.AlgebraicParser;

import java.util.Scanner;

public class CliMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlgebraicParser algebraicParser = AlgebraicParser.getInstance();
        boolean working = true;

        CommandLineHandler handler = new CommandLineHandler();
        while (working) {
            String cmd = scanner.nextLine();
            if (cmd.equals("Q")) {
                working = false;
            } else {
                ActionData data = null;
                switch (cmd) {
                    case "LD" -> data = new ActionData("LD", scanner.nextLine());
                    case "SV" -> data = new ActionData("SV", scanner.nextLine());
                    case "MM" -> data = new ActionData("MM", algebraicParser.parseLongAlgebraic(scanner.nextLine(), handler.getColor()));
                    case "QT" -> working = false;
                    case "DL" -> data = new ActionData("DL", null);
                    case "GB" -> data = new ActionData("GB", Integer.parseInt(scanner.nextLine()));
                    case "PM" -> data = new ActionData("PM", null);
                    case "PH" -> data = new ActionData("PH", null);
                    case "JB" -> data = new ActionData("JB", null);
                    case "JF" -> data = new ActionData("JF", null);
                    case "Help" -> data = new ActionData("HP", null);
                    default -> System.out.print("Unknown code MAIN");
                }

                if (data != null) {
                    handler.makeAction(data);
                } else {
                    System.out.print("Cmd not found -> not executed");
                    System.out.print(cmd);
                }
                handler.display();
            }
        }
    }
}
