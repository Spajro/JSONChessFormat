package chess.color;

public class White implements Color {
    White() {
    }

    @Override
    public Color swap() {
        return black;
    }

    @Override
    public boolean isWhite() {
        return true;
    }

    @Override
    public boolean isBlack() {
        return false;
    }
}
