package cli;

public class ActionData {
    private final String code;
    private final Object parameter;

    public ActionData(String C, Object P) {
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
