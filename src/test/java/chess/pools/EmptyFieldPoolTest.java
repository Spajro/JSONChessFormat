package chess.pools;

import chess.Position;
import chess.board.fields.EmptyField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyFieldPoolTest {

    @Test
    public void shouldReturnCorrectEmptyFieldWhen44Test() {
        assertEquals(new EmptyField(Position.of(4, 4)), new EmptyFieldPool().get(Position.of(4, 4)));
    }

    @Test
    public void shouldReturnCorrectEmptyFieldWhen27Test() {
        assertEquals(new EmptyField(Position.of(2, 7)), new EmptyFieldPool().get(Position.of(2, 7)));
    }

    @Test
    public void shouldReturnCorrectEmptyFieldWhen63Test() {
        assertEquals(new EmptyField(Position.of(6, 3)), new EmptyFieldPool().get(Position.of(6, 3)));
    }

    @Test
    public void shouldReturnCorrectEmptyFieldWhen11Test() {
        assertEquals(new EmptyField(Position.of(1, 1)), new EmptyFieldPool().get(Position.of(1, 1)));
    }

    @Test
    public void shouldReturnCorrectEmptyFieldWhen88Test() {
        assertEquals(new EmptyField(Position.of(8, 8)), new EmptyFieldPool().get(Position.of(8, 8)));
    }

    @Test
    public void shouldThrowExceptionForNotPooledEmptyFieldTest() {
        assertThrows(IllegalArgumentException.class, () -> new EmptyFieldPool().get(Position.of(-4, 4)));
    }

}