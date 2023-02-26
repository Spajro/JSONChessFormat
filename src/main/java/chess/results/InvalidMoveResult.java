package chess.results;

public class InvalidMoveResult implements MoveResult {
    public InvalidMoveResult() {
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
