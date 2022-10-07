package data.annotations;

import chess.Position;

public class FieldAnnotation extends Position implements GraphicAnnotation{
    private final DrawColor color;

    public FieldAnnotation(Position position,DrawColor color) {
        super(position);
        this.color = color;
    }

    @Override
    public DrawColor getColor() {
        return color;
    }
}
