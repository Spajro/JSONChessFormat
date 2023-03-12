package gui.scaling;

import static java.lang.Math.sqrt;

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

    public Vector getFirstPerpendicular() {
        return new Vector(-y, x);
    }

    public Vector getSecondPerpendicular() {
        return new Vector(y, -x);
    }
}


