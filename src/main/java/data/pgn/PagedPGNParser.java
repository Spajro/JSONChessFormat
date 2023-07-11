package data.pgn;

import chess.moves.valid.executable.ExecutableMove;

import java.util.*;

public class PagedPGNParser implements Iterator<ParsedPGN> {
    private final Deque<String> parts;
    private final int size;

    public PagedPGNParser(String pgn) {
        String newLine = PGNParser.getInstance().getEndLineCharacter(pgn).orElseThrow();
        parts = new ArrayDeque<>(List.of(pgn.split(newLine + newLine)));
        size = parts.size();
    }

    @Override
    public boolean hasNext() {
        return parts.size() > 1;
    }

    @Override
    public ParsedPGN next() {
        if(parts.size()<2){
            throw new IllegalStateException();
        }
        String metadata = parts.poll();
        String moves = parts.poll();
        Optional<ArrayDeque<ExecutableMove>> executableMoves = PGNParser.getInstance().parseMoves(moves);
        int length;
        if (executableMoves.isEmpty()) {
            length = -1;
        } else {
            length = executableMoves.get().size();
        }
        return new ParsedPGN(
                PGNParser.getInstance().parseMetadata(metadata, length),
                executableMoves
        );
    }

    public int initialSize() {
        return size;
    }
}
