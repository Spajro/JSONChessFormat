package data.annotations;

import chess.Position;

public class FieldAnnotation extends Position implements GraphicAnnotation {
    private final DrawColor color;

    public FieldAnnotation(Position position, DrawColor color) {
        super(position.getX(), position.getY());
        this.color = color;
    }

    public boolean positionEquals(Position position) {
        return getX() == position.getX() && getY() == position.getY();
    }

    @Override
    public DrawColor getColor() {
        return color;
    }
}
