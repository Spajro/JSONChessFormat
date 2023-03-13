package gui.scaling;

import data.annotations.GraphicAnnotation;

public interface DrawableLine {
    DrawablePoint getStart();

    DrawablePoint getEnd();

    GraphicAnnotation.DrawColor getColor();
}
