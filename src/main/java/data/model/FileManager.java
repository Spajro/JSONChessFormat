package data.model;

import data.json.JsonParser;
import log.Log;

import java.io.*;
import java.util.Scanner;

public class FileManager {
    private final JsonParser parser = new JsonParser();

    public Diagram load(String filename) throws FileNotFoundException {
        Log.log().info("Loading");
        Scanner scanner = new Scanner(new File(filename + ".json"));
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        return parser.parseJson(text.trim());
    }

    public void save(String filename,String json) {
        Log.log().info("saving");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".json"));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
