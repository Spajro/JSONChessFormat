package prg;

public class Kontroler {
    Mode Current;
    public Kontroler(){
        Current=null;
    }
    public void Get_cmd(Action_data A){
        if(A.Get_code().length()==3){
            switch (A.Get_code()){
                case "SEL":
                    Select_Mode((Action_data) A.Get_param()[0]);
                    break;
                default:
                    System.out.print("Unknown code GCMD");
            }
        }
        else{
            Current.Make_action(A);
        }
    }
    void Select_Mode(Action_data A){
        switch (A.Get_code()){
            case "DTE":
                Current=new Database_editor((String)A.Get_param()[0]);
                break;
            default:
                System.out.print("Unknown code SM");
        }
    }
}
