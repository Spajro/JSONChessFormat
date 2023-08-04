package log;

import java.util.function.Supplier;

public class TimeLogSupplier<T> {
    private final Supplier<SizedResult<T>> operation;
    private final String logText;

    public TimeLogSupplier(Supplier<SizedResult<T>> operation, String logText) {
        this.operation = operation;
        this.logText = logText;
    }

    public T apply() {
        long startTime = System.nanoTime();
        SizedResult<T> result = operation.get();
        long endTime = System.nanoTime();
        long nanoDuration = (endTime - startTime);
        double secondDuration = ((double) nanoDuration / Math.pow(10, 9));
        double nodesPerSec = result.size / secondDuration;
        Log.debug(logText + secondDuration + "s with speed: " + nodesPerSec + "gps"); //TODO FOR DEBUG
        return result.result;
    }

    public record SizedResult<T>(int size, T result) {
    }
}
