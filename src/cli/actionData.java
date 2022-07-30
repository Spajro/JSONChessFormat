package cli;

public class actionData {
    String code;
    Object parameter;

    public actionData(String C, Object P) {
        code = C;
        parameter = P;
    }

    public String getCode() {
        return code;
    }

    public Object getParam() {
        return parameter;
    }
}
