package dts.color;

public interface Color {
    White white = new White();
    Black black = new Black();

    default White getWhite() {
        return white;
    }

    default Black getBlack() {
        return black;
    }

    Color swap();

    boolean isWhite();

    boolean isBlack();
}
