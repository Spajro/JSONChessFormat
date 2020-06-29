package prg;

import dts.Bufor;
import dts.Diagram;

public interface Mode {
    void Make_action(Action_data AD);
    Display_data Display();
    void Exit();
    //tmp
    Diagram getDiag();
    boolean GetColor();
}
