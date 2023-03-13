package chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void isBetweenRowTrueTest(){
        assertTrue(new Position(3,3).isBetween(new Position(3,2),new Position(3,7)));
    }

    @Test
    void isBetweenColumnTrueTest(){
        assertTrue(new Position(3,3).isBetween(new Position(2,3),new Position(5,3)));
    }

    @Test
    void isBetweenDiagonalTrue1Test(){
        assertTrue(new Position(3,3).isBetween(new Position(1,1),new Position(5,5)));
    }

    @Test
    void isBetweenDiagonalTrue2Test(){
        assertTrue(new Position(3,4).isBetween(new Position(1,2),new Position(5,6)));
    }

    @Test
    void isBetweenRowFalseTest(){
        assertFalse(new Position(2,3).isBetween(new Position(3,2),new Position(3,7)));
    }

    @Test
    void isBetweenColumnFalseTest(){
        assertFalse(new Position(1,3).isBetween(new Position(2,3),new Position(5,3)));
    }

    @Test
    void isBetweenDiagonalFalse1Test(){
        assertFalse(new Position(7,7).isBetween(new Position(1,1),new Position(5,5)));
    }

    @Test
    void isBetweenDiagonalFalse2Test(){
        assertFalse(new Position(3,1).isBetween(new Position(1,2),new Position(5,6)));
    }

    @Test
    void isBetweenAnyFalseTest(){
        assertFalse(new Position(4,6).isBetween(new Position(2,5),new Position(5,8)));
    }
}