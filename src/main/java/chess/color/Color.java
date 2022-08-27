package chess.color;

import java.io.Serializable;

public interface Color extends Serializable {
    White white = new White();
    Black black = new Black();

    default boolean equal(Color color){
        return this.isWhite()==color.isWhite();
    }

    Color swap();

    boolean isWhite();

    boolean isBlack();
}
