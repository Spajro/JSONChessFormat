package chess.board.requirements;

import chess.color.Color;

import java.util.Objects;

public class CastleRequirements {
    private final boolean whiteShort;
    private final boolean whiteLong;
    private final boolean blackShort;
    private final boolean blackLong;

    public CastleRequirements() {
        whiteShort = true;
        whiteLong = true;
        blackShort = true;
        blackLong = true;
    }

    public CastleRequirements(boolean whiteShort, boolean whiteLong, boolean blackShort, boolean blackLong) {
        this.whiteShort = whiteShort;
        this.whiteLong = whiteLong;
        this.blackShort = blackShort;
        this.blackLong = blackLong;
    }

    public boolean canCastleWhiteShort() {
        return whiteShort;
    }

    public boolean canCastleWhiteLong() {
        return whiteLong;
    }

    public boolean canCastleBlackShort() {
        return blackShort;
    }

    public boolean canCastleBlackLong() {
        return blackLong;
    }

    public CastleRequirements kingMoved(Color color) {
        if (color.isWhite()) {
            return new CastleRequirements(false, false, blackShort, blackLong);
        } else {
            return new CastleRequirements(whiteShort, whiteLong, false, false);
        }
    }

    public CastleRequirements hColumnRookMoved(Color color) {
        if (color.isWhite()) {
            return new CastleRequirements(false, whiteLong, blackShort, blackLong);
        } else {
            return new CastleRequirements(whiteShort, whiteLong, false, blackLong);
        }
    }

    public CastleRequirements aColumnRookMoved(Color color) {
        if (color.isWhite()) {
            return new CastleRequirements(whiteShort, false, blackShort, blackLong);
        } else {
            return new CastleRequirements(whiteShort, whiteLong, blackShort, false);
        }
    }

    public CastleRequirements copy() {
        return new CastleRequirements(whiteShort, whiteLong, blackShort, blackLong);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CastleRequirements that = (CastleRequirements) o;
        return whiteShort == that.whiteShort && whiteLong == that.whiteLong && blackShort == that.blackShort && blackLong == that.blackLong;
    }

    @Override
    public int hashCode() {
        return Objects.hash(whiteShort, whiteLong, blackShort, blackLong);
    }
}
