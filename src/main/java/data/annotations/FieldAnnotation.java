package data.annotations;

import chess.Position;
import data.Jsonable;

public class FieldAnnotation extends Position implements GraphicAnnotation, Jsonable {
    private final DrawColor color;

    public FieldAnnotation(Position position, DrawColor color) {
        super(position);
        this.color = color;
    }

    @Override
    public DrawColor getColor() {
        return color;
    }

    @Override
    public String toJson() {
        return "{\"x\":\"" + x + "\",\"y\":\"" + y + "\"}";
    }
}
