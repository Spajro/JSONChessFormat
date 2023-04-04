package data.annotations;

import chess.moves.RawMove;

public class ArrowAnnotation extends RawMove implements GraphicAnnotation {
    private final DrawColor color;

    public ArrowAnnotation(RawMove move, DrawColor color) {
        super(move);
        this.color = color;
    }

    public boolean moveEquals(RawMove move) {
        return getStartPosition().equals(move.getStartPosition()) && getEndPosition().equals(move.getEndPosition());
    }

    @Override
    public DrawColor getColor() {
        return color;
    }
}
