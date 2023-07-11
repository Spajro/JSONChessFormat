package chess.pools;

import chess.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionPoolTest {

    @Test
    public void shouldReturnCorrectPositionWhen44Test(){
        assertEquals(new Position(4,4),new PositionPool().get(4,4));
    }

    @Test
    public void shouldReturnCorrectPositionWhen27Test(){
        assertEquals(new Position(2,7),new PositionPool().get(2,7));
    }

    @Test
    public void shouldReturnCorrectPositionWhen63Test(){
        assertEquals(new Position(6,3),new PositionPool().get(6,3));
    }

    @Test
    public void shouldReturnCorrectPositionWhen11Test(){
        assertEquals(new Position(1,1),new PositionPool().get(1,1));
    }

    @Test
    public void shouldReturnCorrectPositionWhen88Test(){
        assertEquals(new Position(8,8),new PositionPool().get(8,8));
    }

    @Test
    public void shouldReturnCorrectPositionForNotPooledPositionTest(){
        assertEquals(new Position(-4,4),new PositionPool().get(-4,4));
    }

}