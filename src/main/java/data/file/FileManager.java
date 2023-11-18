package data.file;

import data.json.GameJsonFactory;
import data.json.PagedJsonFactory;
import data.model.DataModel;
import data.model.diagrams.Diagram;
import data.model.metadata.GameData;
import data.pgn.PGNParser;
import data.json.JsonParser;
import data.pgn.PagedPGNParser;
import data.pgn.ParsedPGN;
import log.Log;
import log.TimeLogRunnable;

import java.io.*;
import java.util.List;

public class FileManager {
    private final JsonParser jsonParser = new JsonParser();
    private final PGNParser pgnParser = PGNParser.getInstance();
    private final GameJsonFactory gameJsonFactory = new GameJsonFactory();

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

    public void save(String filename, DataModel dataModel) {
        Log.log().info("Saving JSON");
        new TimeLogRunnable(
                () -> {
                    new PagedWriter(new PagedJsonFactory(dataModel.getActualNode().getRoot()), filename + ".json").write();
                    return dataModel.getGames().size();
                },
                "Saving json to file "
        ).apply();
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

    public void export(String filename, Diagram diagram, GameData gameData) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename + ".json"));
            bufferedWriter.write(gameJsonFactory.gameToJson(diagram, gameData));
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
