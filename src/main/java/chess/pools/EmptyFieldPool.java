package chess.pools;

import chess.Position;
import chess.board.fields.EmptyField;

import java.util.ArrayList;
import java.util.List;

public class EmptyFieldPool {
    private final List<EmptyField> emptyFields = new ArrayList<>();

    EmptyFieldPool() {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                emptyFields.add(new EmptyField(Position.of(i, j)));
            }
        }
    }

    public EmptyField get(Position position) {
        int x = position.getX();
        int y = position.getY();
        if (0 < x && x < 9 && 0 < y && y < 9) {
            return emptyFields.get(x * 8 + y - 9);
        }
        throw new IllegalArgumentException("Illegal EmptyField");

    }
}
