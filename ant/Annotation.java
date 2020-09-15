package ant;

import java.io.Serializable;

public class Annotation implements Serializable {
    //annotacje do pozycji, zarowno tekstowe, znakowe jak i graficzne, strzalki zaznaczenia itd
    String Text;
    GraphicAnnotation[] Graf;
    SingAnnotation[] Signs;
}
