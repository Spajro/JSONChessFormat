package prg;

public class Action_data {
    String Code;
    Object Parameter;
    public Action_data(String C, Object P){
        Code=C;
        Parameter =P;
    }
    public String Get_code(){return Code;}
    public Object Get_param(){return Parameter;}
}
