package data.annotations;

import chess.moves.RawMove;
import data.json.Jsonable;

public class ArrowAnnotation extends RawMove implements GraphicAnnotation, Jsonable {
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

    @Override
    public String toJson() {
        return "{" +
                "\"start\":{\"x\":\"" +
                getStartPosition().getX() +
                "\",\"y\":\"" +
                getStartPosition().getY() +
                "\"},\"end\":{\"x\":" +
                getEndPosition().getX() +
                "\",\"y\":\"" +
                getEndPosition().getY() +
                "\"}}";
    }
}
