package data.dts.moves;

import data.dts.Position;

public class RawMove {
    private final Position startPosition;
    private final Position endPosition;

    public RawMove(Position startPosition, Position endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public boolean isEmpty(){
        return startPosition.isEmpty() || endPosition.isEmpty();
    }
}
