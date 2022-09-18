package log;

import static log.Log.LogLevel.*;

public class CommandLineLog implements Log {
    private static final LogLevel logLevel = WARN;

    CommandLineLog() {
    }

    @Override
    public void info(String message) {
        System.out.print("[INFO] " + message);
    }

    @Override
    public void warn(String message) {
        if (logLevel == WARN || logLevel == FAIL) {
            System.out.print("WARN " + message);
        }
    }

    @Override
    public void fail(String message) {
        if (logLevel == FAIL) {
            System.err.print("ERROR " + message);
        }
    }
}
