package chess.pools;

import chess.Position;
import chess.board.fields.OccupiedField;
import chess.color.Color;
import chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class OccupiedFieldPool {
    private final List<OccupiedField> fields = new ArrayList<>();

    OccupiedFieldPool(PiecePool piecePool) {
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                for (int t = 1; t <= 6; t++) {
                    fields.add(new OccupiedField(piecePool.get(Position.of(x, y), Color.white, typeToInt(t))));
                }
                fields.add(null);
                fields.add(null);
            }
        }
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                for (int t = 1; t <= 6; t++) {
                    fields.add(new OccupiedField(piecePool.get(Position.of(x, y), Color.black, typeToInt(t))));
                }
                fields.add(null);
                fields.add(null);
            }
        }
    }

    public OccupiedField get(Position position, Color color, Piece.Type type) {
        int x = position.getX();
        int y = position.getY();
        int t = typeToInt(type);
        int c;
        if (color.isWhite()) {
            c = 1;
        } else {
            c = 2;
        }
        if (0 < x && x < 9 && 0 < y && y < 9) {
            return fields.get(c * 8 * 8 * 8 + x * 8 * 8 + y * 8 + t - 8 * 8 * 8 - 8 * 8 - 8 - 1);
        }
        throw new IllegalArgumentException("Illegal EmptyField");
    }

    private Piece.Type typeToInt(int type) {
        return switch (type) {
            case 1 -> Piece.Type.PAWN;
            case 2 -> Piece.Type.KNIGHT;
            case 3 -> Piece.Type.BISHOP;
            case 4 -> Piece.Type.ROOK;
            case 5 -> Piece.Type.QUEEN;
            case 6 -> Piece.Type.KING;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    private int typeToInt(Piece.Type type) {
        return switch (type) {
            case PAWN -> 1;
            case KNIGHT -> 2;
            case BISHOP -> 3;
            case ROOK -> 4;
            case QUEEN -> 5;
            case KING -> 6;
        };
    }
}
