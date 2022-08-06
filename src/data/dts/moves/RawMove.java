package data.dts.moves;

import data.dts.Position;

public class RawMove {
    private final Position oldPosition;
    private final Position newPosition;

    public RawMove(Position oldPosition, Position newPosition) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    public Position getOldPosition() {
        return oldPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }
}
