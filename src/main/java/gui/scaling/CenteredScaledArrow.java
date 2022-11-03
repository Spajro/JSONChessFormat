package gui.scaling;

import data.annotations.GraphicAnnotation;

public class CenteredScaledArrow {
    private final CenteredScaledPosition start;
    private final CenteredScaledPosition end;
    private final GraphicAnnotation.DrawColor drawColor;


    public CenteredScaledArrow(ScaledArrow scaledArrow) {
        this.start = new CenteredScaledPosition(scaledArrow.getStart());
        this.end = new CenteredScaledPosition(scaledArrow.getEnd());
        this.drawColor = scaledArrow.getColor();
    }

    public CenteredScaledPosition getStart() {
        return start;
    }

    public CenteredScaledPosition getEnd() {
        return end;
    }

    public GraphicAnnotation.DrawColor getColor() {
        return drawColor;
    }

    @Override
    public String toString() {
        return "CS<" + start + ">,<" + end + ">";
    }
}
