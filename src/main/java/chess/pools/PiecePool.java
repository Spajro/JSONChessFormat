package chess.pools;

import chess.Position;
import chess.color.Color;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class PiecePool {
    private final List<Piece> pieces = new ArrayList<>();

    PiecePool() {
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                for (int t = 1; t <= 6; t++) {
                    pieces.add(pieceFromType(Color.white, Position.of(x, y), t));
                }
                pieces.add(null);
                pieces.add(null);
            }
        }
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                for (int t = 1; t <= 6; t++) {
                    pieces.add(pieceFromType(Color.black, Position.of(x, y), t));
                }
                pieces.add(null);
                pieces.add(null);
            }
        }
    }

    public Piece get(Position position, Color color, Piece.Type type) {
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
            return pieces.get(c * 8 * 8 * 8 + x * 8 * 8 + y * 8 + t - 8 * 8 * 8 - 8 * 8 - 8-1);
        }
        throw new IllegalArgumentException("Illegal EmptyField");
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

    private Piece pieceFromType(Color color, Position position, int type) {
        return switch (type) {
            case 1 -> new Pawn(color, position);
            case 2 -> new Knight(color, position);
            case 3 -> new Bishop(color, position);
            case 4 -> new Rook(color, position);
            case 5 -> new Queen(color, position);
            case 6 -> new King(color, position);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
