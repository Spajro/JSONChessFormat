package prg;

import dis.DisplayData;

public interface Mode {
    void Make_action(Action_data AD);
    DisplayData Display();
    void Exit();

    //tmp
  //  Diagram GetDiag();
   // boolean GetColor();
}
