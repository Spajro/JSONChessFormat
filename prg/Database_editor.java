package prg;

import dts.Diagram;
import dts.Move;
import dts.Start_pose;

public class Database_editor implements Mode{
    Diagram Base;

    Database_editor(){
        Base= new Start_pose();
    }
    @Override
    public void Make_action(Action_data AD) {
        //TODO
        //dokonczyc switcha
        switch (AD.Get_code()){
            case "LD":
                Base=Load();
                break;

            case "SV":
                Save();
                break;
        }
    }

    @Override
    public Display_data Display() {
        //TODO
        return null;
    }

    @Override
    public void Exit() {
    //TODO
    }
    Diagram Load(){
        //TODO
        return null;
    }
    void Save(){
        //TODO
    }
    void Make_move(Move M){
        //TODO
    }
    void Annotate(){
        //TODO
    }
}
