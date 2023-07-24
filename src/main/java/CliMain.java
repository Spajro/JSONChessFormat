import cli.CommandLineHandler;

import java.util.Scanner;

public class CliMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandLineHandler handler = new CommandLineHandler();

        while (true) {
            handler.handle(scanner.nextLine());
        }
    }
}
