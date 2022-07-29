package src.data.dts.fields;

import src.data.dts.pieces.Piece;

public class EmptyField implements Field{
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Piece getPiece() {
        return null;
    }
}
