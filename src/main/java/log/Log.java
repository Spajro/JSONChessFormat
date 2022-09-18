package log;

public interface Log {
    static Log log() {
        return new CommandLineLog();
    }

    void info(String message);

    void warn(String message);

    void fail(String message);

    enum LogLevel {
        INFO, WARN, FAIL
    }
}
