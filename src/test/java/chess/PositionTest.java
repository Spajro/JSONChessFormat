package chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void isBetweenRowTrueTest(){
        assertTrue(Position.of(3,3).isBetween(Position.of(3,2), Position.of(3,7)));
    }

    @Test
    void isBetweenColumnTrueTest(){
        assertTrue(Position.of(3,3).isBetween(Position.of(2,3), Position.of(5,3)));
    }

    @Test
    void isBetweenDiagonalTrue1Test(){
        assertTrue(Position.of(3,3).isBetween(Position.of(1,1), Position.of(5,5)));
    }

    @Test
    void isBetweenDiagonalTrue2Test(){
        assertTrue(Position.of(3,4).isBetween(Position.of(1,2), Position.of(5,6)));
    }

    @Test
    void isBetweenRowFalseTest(){
        assertFalse(Position.of(2,3).isBetween(Position.of(3,2), Position.of(3,7)));
    }

    @Test
    void isBetweenColumnFalseTest(){
        assertFalse(Position.of(1,3).isBetween(Position.of(2,3), Position.of(5,3)));
    }

    @Test
    void isBetweenDiagonalFalse1Test(){
        assertFalse(Position.of(7,7).isBetween(Position.of(1,1), Position.of(5,5)));
    }

    @Test
    void isBetweenDiagonalFalse2Test(){
        assertFalse(Position.of(3,1).isBetween(Position.of(1,2), Position.of(5,6)));
    }

    @Test
    void isBetweenAnyFalseTest(){
        assertFalse(Position.of(4,6).isBetween(Position.of(2,5), Position.of(5,8)));
    }
}