package data.annotations;

public interface GraphicAnnotation {
    enum DrawColor {
        BLUE, RED, GREEN, YELLOW
    }

    DrawColor getColor();

    static String drawColorToString(DrawColor color) {
        return switch (color) {
            case BLUE -> "blue";
            case RED -> "red";
            case GREEN -> "green";
            case YELLOW -> "yellow";
        };
    }
}
