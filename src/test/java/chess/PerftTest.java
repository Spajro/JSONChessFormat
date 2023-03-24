package chess;

import chess.board.ChessBoard;
import chess.utility.FENTranslator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class PerftTest {

    private static final String END_FEN = "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - ";

    long perft(ChessBoard board, int n) {
        if (n == 1) {
            return board.getGenerator().getAllPossibleValidMoves().size();
        } else {
            return board.getGenerator().getAllPossibleValidMoves().stream()
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
    void perftStart1Test() {
        long score = timedPerft(new ChessBoard(), 1);
        System.out.println("Deviation for n = " + 1 + " is " + deviation(20, score));
        assertEquals(20, score);
    }

    @Test
    void perftStart2Test() {
        long score = timedPerft(new ChessBoard(), 2);
        System.out.println("Deviation for n = " + 2 + " is " + deviation(400, score));
        assertEquals(400, score);
    }

    @Test
    void perftStart3Test() {
        long score = timedPerft(new ChessBoard(), 3);
        System.out.println("Deviation for n = " + 3 + " is " + deviation(8902, score));
        assertEquals(8902, score);
    }

    @Test
    void perftStart4Test() {
        long score = timedPerft(new ChessBoard(), 4);
        System.out.println("Deviation for n = " + 4 + " is " + deviation(197281, score));
        assertEquals(197281, score);
    }

    @Test
    void perftStart5Test() {
        long score = timedPerft(new ChessBoard(), 5);
        System.out.println("Deviation for n = " + 5 + " is " + deviation(4865609, score));
        assertEquals(4865609, score);
    }

    @Disabled
    @Test
    void perftStart6Test() {
        long score = timedPerft(new ChessBoard(), 6);
        System.out.println("Deviation for n = " + 6 + " is " + deviation(119060324, score));
        assertEquals(119060324, score);
    }

    @Test
    void perftEnd1Test() {
        long score = timedPerft(new FENTranslator().parseFEN(END_FEN), 1);
        System.out.println("Deviation for n = " + 1 + " is " + deviation(14, score));
        assertEquals(14, score);
    }

    @Test
    void perftEnd2Test() {
        long score = timedPerft(new FENTranslator().parseFEN(END_FEN), 2);
        System.out.println("Deviation for n = " + 2 + " is " + deviation(191, score));
        assertEquals(191, score);
    }

    @Test
    void perftEnd3Test() {
        long score = timedPerft(new FENTranslator().parseFEN(END_FEN), 3);
        System.out.println("Deviation for n = " + 3 + " is " + deviation(2812, score));
        assertEquals(2812, score);
    }

    @Test
    void perftEnd4Test() {
        long score = timedPerft(new FENTranslator().parseFEN(END_FEN), 4);
        System.out.println("Deviation for n = " + 4 + " is " + deviation(43238, score));
        assertEquals(43238, score);
    }

    @Test
    void perftEnd5Test() {
        long score = timedPerft(new FENTranslator().parseFEN(END_FEN), 5);
        System.out.println("Deviation for n = " + 5 + " is " + deviation(674624, score));
        assertEquals(674624, score);
    }
}
