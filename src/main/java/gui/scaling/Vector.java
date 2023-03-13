package gui.scaling;

import static java.lang.Math.*;

public class Vector {
    private final int x;
    private final int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public NormalizedVector normalize() {
        return new NormalizedVector(this);
    }

    public int length() {
        return (int) sqrt(x * x + y * y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector rotate(int degrees) {
        double angle = degrees * PI / 180;
        return new Vector((int) (x * cos(angle) - y * sin(angle)), (int) (x * sin(angle) + y * cos(angle)));
    }
}
