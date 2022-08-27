package data.annotations;

import chess.Position;
import chess.moves.RawMove;

public class ArrowAnnotation extends RawMove implements GraphicAnnotation {
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
}
