package data.annotations;

import chess.moves.Vector;

public class ArrowAnnotation extends Vector implements GraphicAnnotation {
    private final DrawColor color;

    public ArrowAnnotation(Vector vector, DrawColor color) {
        super(vector);
        this.color = color;
    }

    public boolean moveEquals(Vector vector) {
        return getStartPosition().equals(vector.getStartPosition()) && getEndPosition().equals(vector.getEndPosition());
    }

    @Override
    public DrawColor getColor() {
        return color;
    }
}
