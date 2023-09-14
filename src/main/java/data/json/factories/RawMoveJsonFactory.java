package data.json.factories;

import chess.formats.algebraic.RawAlgebraicFactory;
import chess.moves.raw.RawMove;

public class RawMoveJsonFactory implements JsonFactory<RawMove> {
    private final RawAlgebraicFactory rawAlgebraicFactory = new RawAlgebraicFactory();

    public String toJson(RawMove rawMove) {
        return "\"" + rawAlgebraicFactory.moveToRawAlgebraic(rawMove) + "\"";
    }
}
