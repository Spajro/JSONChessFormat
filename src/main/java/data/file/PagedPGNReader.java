package data.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class PagedPGNReader implements Iterator<PGNGame> {
    private static final int CHUNK_SIZE = 1000;
    private final BufferedReader reader;
    private final Deque<String> read = new ArrayDeque<>();

    public PagedPGNReader(String filename) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(filename));
        upload();
    }

    @Override
    public boolean hasNext() {
        if (read.size()<100) {
            upload();
        }
        return !read.isEmpty();
    }

    @Override
    public PGNGame next() {
        StringBuilder metadata = new StringBuilder();
        String moves;
        String line = read.poll();
        while (!line.isEmpty()) {
            metadata.append(line);
            metadata.append('\n');
            line = read.poll();
        }
        moves = read.poll();
        if (!read.isEmpty()) {
            read.pop();
        }
        return new PGNGame(metadata.toString(), moves);
    }

    private void upload() {
        for (int i = 0; i < CHUNK_SIZE; i++) {
            try {
                String line = reader.readLine();
                if (line != null) {
                    read.add(line);
                } else {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
