package chess;

import chess.board.ChessBoard;
import chess.formats.fen.FENParser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PerftTest {

    void perftCase(String fen, long expected, int n) {
        long actual = timedPerft(FENParser.getInstance().parseFEN(fen), n);
        System.out.println("Deviation for n = " + n + " is " + deviation(expected, actual));
        assertEquals(expected, actual);
    }

    long perft(ChessBoard board, int n) {
        if (n == 1) {
            return board.getGenerator().getAllPossibleExecutableMoves().size();
        } else {
            return board.getGenerator().getAllPossibleExecutableMoves().stream()
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

    @Nested
    class StartTest {
        String START_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - ";

        @Test
        void perft1Test() {
            perftCase(START_FEN, 20, 1);
        }

        @Test
        void perft2Test() {
            perftCase(START_FEN, 400, 2);
        }

        @Test
        void perft3Test() {
            perftCase(START_FEN, 8902, 3);
        }

        @Test
        void perft4Test() {
            perftCase(START_FEN, 197281, 4);
        }

        @Test
        void perft5Test() {
            perftCase(START_FEN, 4865609, 5);
        }

        @Disabled
        @Test
        void perft6Test() {
            perftCase(START_FEN, 119060324, 6);
        }
    }

    @Nested
    class EndTest {
        private static final String END_FEN = "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - ";

        @Test
        void perft1Test() {
            perftCase(END_FEN, 14, 1);
        }

        @Test
        void perft2Test() {
            perftCase(END_FEN, 191, 2);
        }

        @Test
        void perft3Test() {
            perftCase(END_FEN, 2812, 3);
        }

        @Test
        void perft4Test() {
            perftCase(END_FEN, 43238, 4);
        }

        @Test
        void perft5Test() {
            perftCase(END_FEN, 674624, 5);
        }
    }

    @Nested
    class MidTest {
        private static final String MID_FEN = "r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq -";

        @Test
        void perft1Test() {
            perftCase(MID_FEN, 6, 1);
        }

        @Test
        void perft2Test() {
            perftCase(MID_FEN, 264, 2);
        }

        @Test
        void perft3Test() {
            perftCase(MID_FEN, 9467, 3);
        }

        @Test
        void perft4Test() {
            perftCase(MID_FEN, 422333, 4);
        }
    }

    @Nested
    class KiwipeteTest {
        private static final String KIWIPETE_FEN = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - ";

        @Test
        void perft1Test() {
            perftCase(KIWIPETE_FEN, 48, 1);
        }

        @Test
        void perft2Test() {
            perftCase(KIWIPETE_FEN, 2039, 2);
        }

        @Test
        void perft3Test() {
            perftCase(KIWIPETE_FEN, 97862, 3);
        }

        @Test
        void perft4Test() {
            perftCase(KIWIPETE_FEN, 4085603, 4);
        }
    }
}
