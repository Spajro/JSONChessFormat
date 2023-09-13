package data.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class PagedWriter {
    private final Iterator<String> iterator;
    private final String filename;

    public PagedWriter(Iterator<String> iterator, String filename) {
        this.iterator = iterator;
        this.filename = filename;
    }

    public void write() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            while (iterator.hasNext()) {
                writer.write(iterator.next());
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
