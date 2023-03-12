package gui.scaling;

import chess.Position;

public class ScaledPosition {
    private final int x;
    private final int y;
    private final int scale;

    public ScaledPosition(Position position, int scale) {
        this.scale = scale;
        x = (position.getX() - 1) * scale;
        y = (9 - position.getY()) * scale;
    }

    public ScaledPosition(int x, int y, int scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getScale() {
        return scale;
    }

    @Override
    public String toString() {
        return "S<" + x + ">,<" + y + ">";
    }
}
