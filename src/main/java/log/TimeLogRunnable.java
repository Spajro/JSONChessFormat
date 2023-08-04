package log;

import java.util.function.Supplier;

public class TimeLogRunnable {
    private final Supplier<Integer> operation;
    private final String logText;

    public TimeLogRunnable(Supplier<Integer> operation, String logText) {
        this.operation = operation;
        this.logText = logText;
    }

    public void apply() {
        long startTime = System.nanoTime();
        int size = operation.get();
        long endTime = System.nanoTime();
        long nanoDuration = (endTime - startTime);
        double secondDuration = ((double) nanoDuration / Math.pow(10, 9));
        double nodesPerSec = size / secondDuration;
        Log.debug(logText + secondDuration + "s with speed: " + nodesPerSec + "gps"); //TODO FOR DEBUG
    }

}
