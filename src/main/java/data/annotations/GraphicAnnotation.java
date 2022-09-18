package data.annotations;

public interface GraphicAnnotation {
     enum DrawColor{
        BLUE,RED,GREEN,YELLOW
    }

    DrawColor getColor();
}
