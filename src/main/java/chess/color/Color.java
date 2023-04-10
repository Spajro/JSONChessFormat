package chess.color;

public interface Color {
    White white = new White();
    Black black = new Black();

    default boolean equal(Color color) {
        return this.isWhite() == color.isWhite();
    }

    Color swap();

    boolean isWhite();

    boolean isBlack();
}
