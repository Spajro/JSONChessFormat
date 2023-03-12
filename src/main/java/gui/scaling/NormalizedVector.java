package gui.scaling;

public class NormalizedVector {
    private final double x;
    private final double y;

    public NormalizedVector(Vector vector) {
        this.x = (double) vector.getX() / (double) vector.length();
        this.y = (double) vector.getY() / (double) vector.length();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector toVector(int length) {
        return new Vector((int) (x * length), (int) (y * length));
    }
}
