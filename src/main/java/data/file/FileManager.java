package data.file;

import data.model.Diagram;
import data.pgn.PGNParser;
import data.json.JsonParser;
import data.pgn.PagedPGNParser;
import data.pgn.ParsedPGN;
import log.Log;

import java.io.*;
import java.util.List;

public class FileManager {
    private final JsonParser jsonParser = new JsonParser();
    private final PGNParser pgnParser = PGNParser.getInstance();

    public Diagram loadJSON(String filename) throws FileNotFoundException {
        Log.log().info("Loading JSON");
        String text = readFile(filename + ".json");
        return jsonParser.parseJson(text.trim());
    }

    public List<ParsedPGN> loadPGN(String filename) throws FileNotFoundException {
        Log.log().info("Loading PGN");
        String text = readFile(filename + ".pgn");
        return pgnParser.parsePGN(text);
    }

    public PagedPGNParser loadPagedPGN(String filename) throws FileNotFoundException {
        Log.log().info("Loading Paged PGN");
        return new PagedPGNParser(new PagedPGNReader(filename + ".pgn"));
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

    private String readFile(String filename) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            StringBuilder result = new StringBuilder();
            while (true) {
                int i = bufferedReader.read();
                if (i == -1) {
                    bufferedReader.close();
                    return result.toString();
                }
                result.append((char) i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
