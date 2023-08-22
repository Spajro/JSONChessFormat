package data.pgn;

import chess.moves.valid.executable.ExecutableMove;
import data.file.PGNGame;

import java.util.*;

public class PagedPGNParser implements Iterator<ParsedPGN> {
    private final Iterator<PGNGame> iterator;

    public PagedPGNParser(Iterator<PGNGame> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public ParsedPGN next() {
        PGNGame pgnGame = iterator.next();
        String metadata = pgnGame.metadata();
        String moves = pgnGame.moves();
        Optional<ArrayDeque<ExecutableMove>> executableMoves = PGNParser.getInstance().parseMoves(moves);
        int length = executableMoves.map(ArrayDeque::size).orElse(-1);
        return new ParsedPGN(
                PGNParser.getInstance().parseMetadata(metadata, length),
                executableMoves
        );
    }
}
