package chess.color;

public class Black implements Color{
    Black() {
    }

    @Override
    public Color swap() {
        return white;
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
