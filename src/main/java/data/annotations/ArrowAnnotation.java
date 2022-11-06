package data.annotations;

import chess.Position;
import chess.moves.RawMove;
import data.json.Jsonable;

public class ArrowAnnotation extends RawMove implements GraphicAnnotation, Jsonable {
    private final DrawColor color;

    public ArrowAnnotation(Position startPosition, Position endPosition, DrawColor color) {
        super(startPosition, endPosition);
        this.color = color;
    }

    public ArrowAnnotation(RawMove move, DrawColor color) {
        super(move);
        this.color = color;
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
