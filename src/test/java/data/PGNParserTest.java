package data;

import chess.moves.valid.executable.ExecutableMove;
import data.pgn.PGNParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PGNParserTest {

    @Test
    void parsePGNTest() {
        String pgn = """
                [Event "ch-RUS Blitz 2017"]
                [Site "Sochi RUS"]
                [Date "2017.10.02"]
                [Round "10.5"]
                [White "Grachev, B."]
                [Black "Fedoseev, Vl3"]
                [Result "1/2-1/2"]
                [WhiteElo "2654"]
                [BlackElo "2731"]
                [Variant "Standard"]
                [TimeControl "-"]
                [ECO "A40"]
                [Opening "Queen's Pawn Game: Modern Defense"]
                [Termination "Normal"]
                                
                1. d4 g6 2. Bf4 d6 3. e3 Bg7 4. Nf3 Nd7 5. h3 e5 6. Bh2 Ne7 7. Be2 O-O 8. O-O b6 9. c4 Bb7 10. Nc3 h6 11. Rc1 g5 12. b4 Ng6 13. dxe5 dxe5 14. Qc2 a5 15. a3 axb4 16. axb4 c5 17. bxc5 Nxc5 18. Rfd1 Qf6 19. Nd5 Qd6 20. Rb1 Bxd5 21. Rxd5 Qe6 22. Rbd1 Qe8 23. Rd6 Na4 24. Bd3 Qe7 25. Bxg6 fxg6 26. Rd7 Qe8 27. Bxe5 Bxe5 28. Nxe5 Nc5 29. R7d5 Kg7 30. Nd7 Nxd7 31. Rxd7+ Rf7 32. Qb2+ Kh7 33. R7d6 Rc8 34. Qd4 Qa4 35. Qxb6 Rxc4 36. Rf1 Qc2 37. Qa6 Rcc7 38. Qb5 Qf5 39. Rd5 Qf6 40. Qd3 Rc3 41. Qd4 Qxd4 42. Rxd4 Rc2 43. g4 Re2 44. Kg2 Kg7 45. Rd6 Kh7 46. Kg3 Rxe3+ 47. fxe3 Rxf1 48. Rd7+ Kg8 49. Kg2 Rf8 50. Re7 Rf6 51. e4 Ra6 52. e5 Kf8 53. Rb7 Ra2+ 54. Kg3 Ra3+ 55. Kg2 Re3 56. Rb5 1/2-1/2
                """;
        Optional<ArrayDeque<ExecutableMove>> node = PGNParser.getInstance().parsePGN(pgn).get(0).moves();
        assertTrue(node.isPresent());
    }

}