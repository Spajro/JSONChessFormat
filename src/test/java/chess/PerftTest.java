package chess;

import chess.board.ChessBoard;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class PerftTest {

    long perft(ChessBoard board, int n) {
        if (n == 1) {
            return board.getGenerator().getAllPossibleValidMoves().size();
        } else {
            return board.getGenerator().getAllPossibleValidMoves().stream()
                    .mapToLong(move -> perft(board.makeMove(move), n - 1))
                    .sum();
        }
    }

    long threadedPerft(ChessBoard board, int n) {
        if (n == 1) {
            return board.getGenerator().getAllPossibleValidMoves().size();
        } else {
            return board.getGenerator().getAllPossibleValidMoves().stream()
                    .map(board::makeMove)
                    .map(chessBoard -> new PerftThread(chessBoard, n-1))
                    .peek(PerftThread::run)
                    .peek(perftThread -> {
                        try {
                            perftThread.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .mapToLong(PerftThread::getResult)
                    .sum();
        }
    }

    long timedPerft(ChessBoard board, int n) {
        long startTime = System.nanoTime();
        long result = threadedPerft(board, n);
        long endTime = System.nanoTime();

        long nanoDuration = (endTime - startTime);
        double secondDuration = ((double) nanoDuration / Math.pow(10, 9));
        double nodesPerSec = ((double) result / secondDuration);
        System.out.println("Perft(" + n + ") in time " + secondDuration + "s and " + result + " nodes resulting with " + nodesPerSec + "nps");
        return result;
    }

    double deviation(long expected, long actual) {
        return ((float) Math.abs(expected - actual)) / expected;
    }

    @Test
    void perft1Test() {
        long score = timedPerft(new ChessBoard(), 1);
        System.out.println("Deviation for n = " + 1 + " is " + deviation(20, score));
        assertEquals(20, score);
    }

    @Test
    void perft2Test() {
        long score = timedPerft(new ChessBoard(), 2);
        System.out.println("Deviation for n = " + 2 + " is " + deviation(400, score));
        assertEquals(400, score);
    }

    @Test
    void perft3Test() {
        long score = timedPerft(new ChessBoard(), 3);
        System.out.println("Deviation for n = " + 3 + " is " + deviation(8902, score));
        assertEquals(8902, score);
    }

    @Test
    void perft4Test() {
        long score = timedPerft(new ChessBoard(), 4);
        System.out.println("Deviation for n = " + 4 + " is " + deviation(197281, score));
        assertEquals(197281, score);
    }

    @Test
    void perft5Test() {
        long score = timedPerft(new ChessBoard(), 5);
        System.out.println("Deviation for n = " + 5 + " is " + deviation(4865609, score));
        assertEquals(4865609, score);
    }

    @Test
    void perft6Test() {
        long score = timedPerft(new ChessBoard(), 6);
        System.out.println("Deviation for n = " + 6 + " is " + deviation(119060324, score));
        assertEquals(119060324, score);
    }

    class PerftThread extends Thread {
        private long result = 0;
        private final ChessBoard chessBoard;
        private final int n;

        public PerftThread(ChessBoard chessBoard, int n) {
            this.chessBoard = chessBoard;
            this.n = n;
        }

        @Override
        public void run() {
            result = perft(chessBoard, n);
        }

        public long getResult() {
            return result;
        }
    }
}
