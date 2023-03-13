package gui.scaling;

public class CenteredScaledPosition implements DrawablePoint {
    private final int x;
    private final int y;
    private final int scale;

    public CenteredScaledPosition(ScaledPosition position) {
        this.x = (int) (position.getX() + 0.5 * position.getScale());
        this.y = (int) (position.getY() - 0.5 * position.getScale());
        this.scale = position.getScale();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }


    public ScaledPosition moveByVector(Vector vector) {
        return new ScaledPosition(x + vector.getX(), y + vector.getY(), scale);
    }

    public ScaledPosition toScaledPosition(){
        return new ScaledPosition(x,y,scale);
    }

    @Override
    public String toString() {
        return "CS<" + x + ">,<" + y + ">";
    }
}

