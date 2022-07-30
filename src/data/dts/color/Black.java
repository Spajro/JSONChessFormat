package data.dts.color;

public class Black implements Color{
    Black() {
    }

    @Override
    public Color swap() {
        return Color.white;
    }

    @Override
    public boolean isWhite() {
        return false;
    }

    @Override
    public boolean isBlack() {
        return true;
    }
}
