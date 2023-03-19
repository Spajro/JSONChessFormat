package gui.board;

import java.awt.event.MouseEvent;

public class SpecialKeysMap {
    private final boolean isShiftPressed;
    private final boolean isAltPressed;
    private final boolean isControlPressed;

    public SpecialKeysMap(MouseEvent event) {
        isShiftPressed = event.isShiftDown();
        isAltPressed = event.isAltDown();
        isControlPressed = event.isControlDown();
    }

    public boolean isAnyPressed() {
        return isShiftPressed || isAltPressed || isControlPressed;
    }

    public boolean isShiftPressed() {
        return isShiftPressed;
    }

    public boolean isAltPressed() {
        return isAltPressed;
    }

    public boolean isControlPressed() {
        return isControlPressed;
    }

    public boolean areAnyTwoPressed() {
        return booleanToInt(isAltPressed) + booleanToInt(isShiftPressed) + booleanToInt(isControlPressed) == 2;
    }

    private int booleanToInt(boolean bool) {
        return bool ? 1 : 0;
    }
}
