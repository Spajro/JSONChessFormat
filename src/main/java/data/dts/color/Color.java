package data.dts.color;

import java.io.Serializable;

public interface Color extends Serializable {
    White white = new White();
    Black black = new Black();

    default White getWhite() {
        return white;
    }

    default Black getBlack() {
        return black;
    }

    default boolean equal(Color color){
        return this.isWhite()==color.isWhite();
    }

    Color swap();

    boolean isWhite();

    boolean isBlack();
}
