package chess;

import chess.board.ChessBoard;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class PerftTest {

    long perft(ChessBoard board, int n) {
        if (n == 1) {
            return board.getAllPossibleValidMoves().size();
        } else {
            return board.getAllPossibleValidMoves().stream()
                    .mapToLong(move -> perft(board.makeMove(move), n - 1))
                    .sum();
        }
    }

    long timedPerft(ChessBoard board, int n) {
        long startTime = System.nanoTime();
        long result = perft(board, n);
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
}
