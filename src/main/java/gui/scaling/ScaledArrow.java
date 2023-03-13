package gui.scaling;

import data.annotations.ArrowAnnotation;
import data.annotations.GraphicAnnotation;

public class ScaledArrow implements DrawableLine {
    private final ScaledPosition start;
    private final ScaledPosition end;
    private final GraphicAnnotation.DrawColor drawColor;

    public ScaledArrow(ArrowAnnotation arrow, int scale) {
        this.drawColor = arrow.getColor();
        start = new ScaledPosition(arrow.getStartPosition(), scale);
        end = new ScaledPosition(arrow.getEndPosition(), scale);
    }

    public ScaledArrow(ScaledPosition start, ScaledPosition end, GraphicAnnotation.DrawColor drawColor) {
        this.start = start;
        this.end = end;
        this.drawColor = drawColor;
    }

    @Override
    public ScaledPosition getStart() {
        return start;
    }

    @Override
    public ScaledPosition getEnd() {
        return end;
    }

    @Override
    public GraphicAnnotation.DrawColor getColor() {
        return drawColor;
    }

    @Override
    public String toString() {
        return "S<" + start + ">,<" + end + ">";
    }
}
