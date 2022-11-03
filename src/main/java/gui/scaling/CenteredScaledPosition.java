package gui.scaling;

public class CenteredScaledPosition {
    private final int x;
    private final int y;

    public CenteredScaledPosition(ScaledPosition position) {
        this.x = (int) (position.getX() + 0.5 * position.getScale());
        this.y = (int) (position.getY() - 0.5 * position.getScale());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "CS<" + x + ">,<" + y + ">";
    }
}

