package gui.scaling;

import chess.moves.RawMove;

public class ScaledLine {
    private final ScaledPosition start;
    private final ScaledPosition end;

    public ScaledLine(RawMove rawMove, int scale) {
        start = new ScaledPosition(rawMove.getStartPosition(), scale);
        end = new ScaledPosition(rawMove.getEndPosition(), scale);
    }

    public ScaledPosition getStart() {
        return start;
    }

    public ScaledPosition getEnd() {
        return end;
    }

    
}
