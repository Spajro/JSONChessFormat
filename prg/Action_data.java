package prg;

public class Action_data {
    String Code;
    Object[] Parameters;
    public Action_data(String C, Object[] P){
        Code=C;
        Parameters=P;
    }
    public String Get_code(){return Code;}
    public Object[] Get_param(){return Parameters;}
}
