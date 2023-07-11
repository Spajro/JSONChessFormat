package chess.pools;

import chess.Position;
import chess.moves.raw.RawMove;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RawMovePoolTest {

    @Test
    public void shouldReturnCorrectRawMoveWhen1111Test() {
        assertEquals(RawMove.of(Position.of(1, 1), Position.of(1, 1)),
                new RawMovePool().get(Position.of(1, 1), Position.of(1, 1)));
    }

    @Test
    public void shouldReturnCorrectPositionWhen4444Test() {
        assertEquals(RawMove.of(Position.of(4, 4), Position.of(4, 4)),
                new RawMovePool().get(Position.of(4, 4), Position.of(4, 4)));
    }

    @Test
    public void shouldReturnCorrectRawMoveWhen2345Test() {
        assertEquals(RawMove.of(Position.of(2, 3), Position.of(4, 5)),
                new RawMovePool().get(Position.of(2, 3), Position.of(4, 5)));
    }

    @Test
    public void shouldReturnCorrectRawMoveWhen8888Test() {
        assertEquals(RawMove.of(Position.of(8, 8), Position.of(8, 8)),
                new RawMovePool().get(Position.of(8, 8), Position.of(8, 8)));
    }
}