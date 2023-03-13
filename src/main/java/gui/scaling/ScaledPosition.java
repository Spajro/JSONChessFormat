package gui.scaling;

import chess.Position;

public class ScaledPosition implements DrawablePoint {
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

    @Override
    public int getX() {
        return x;
    }

    @Override
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
