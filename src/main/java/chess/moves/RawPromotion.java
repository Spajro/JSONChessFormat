package chess.moves;

import chess.Position;

import static chess.pieces.Piece.*;

public class RawPromotion extends RawMove {
    private final Type type;

    public RawPromotion(Position startPosition, Position endPosition, Type type) {
        super(startPosition, endPosition);
        this.type = type;
    }

    public RawPromotion(RawMove move, Type type) {
        super(move);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "<" + getStartPosition() + "," + getEndPosition() + " = " + type + ">";
    }
}
