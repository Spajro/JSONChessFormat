package data.model;

import data.pgn.PGNParser;
import data.json.JsonParser;
import data.pgn.ParsedPGN;
import log.Log;

import java.io.*;
import java.util.Scanner;

public class FileManager {
    private final JsonParser jsonParser = new JsonParser();
    private final PGNParser pgnParser = PGNParser.getInstance();

    public Diagram loadJSON(String filename) throws FileNotFoundException {
        Log.log().info("Loading JSON");
        Scanner scanner = new Scanner(new File(filename + ".json"));
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        return jsonParser.parseJson(text.trim());
    }

    public ParsedPGN loadPGN(String filename) throws FileNotFoundException {
        Log.log().info("Loading PGN");
        Scanner scanner = new Scanner(new File(filename + ".pgn"));
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        return pgnParser.parsePGN(text);
    }

    public void save(String filename, String json) {
        Log.log().info("Saving JSON");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".json"));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
