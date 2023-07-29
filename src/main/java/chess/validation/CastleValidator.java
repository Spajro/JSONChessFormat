package chess.validation;

import chess.Position;
import chess.board.requirements.CastleRequirements;
import chess.board.ChessBoard;
import chess.board.fields.Field;
import chess.moves.raw.RawMove;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CastleValidator {
    private final ChessBoard chessBoard;

    enum CastleType {
        SHORT, LONG
    }

    public CastleValidator(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public boolean isLegalCastle(RawMove move) {
        Optional<CastleType> optionalCastleType = moveToType(move);
        if (optionalCastleType.isPresent()) {
            CastleType castleType = optionalCastleType.get();
            return chessBoard.getKingPosition(chessBoard.getColor()).equals(move.getStartPosition())
                    && isCastleLegalInRequirements(castleType)
                    && !anyPositionKingsPassesIsAttacked(castleType)
                    && !anyPositionBetweenKingAndRookIsOccupied(castleType);
        } else {
            return false;
        }
    }

    public Optional<CastleType> moveToType(RawMove move) {
        if (move.getStartPosition().equals(chessBoard.getKingPosition(chessBoard.getColor()))) {
            if (move.getEndPosition().getX() == move.getStartPosition().getX() + 2) {
                return Optional.of(CastleType.SHORT);
            }
            if (move.getEndPosition().getX() == move.getStartPosition().getX() - 2) {
                return Optional.of(CastleType.LONG);
            }
        }
        return Optional.empty();
    }

    private boolean anyPositionKingsPassesIsAttacked(CastleType castleType) {
        return positionsKingPasses(castleType).stream()
                .anyMatch(position -> chessBoard.getUtility().isPositionAttacked(position, chessBoard.getColor().swap()));
    }

    private boolean anyPositionBetweenKingAndRookIsOccupied(CastleType castleType) {
        return positionsBetweenKingAndRook(castleType).stream()
                .map(chessBoard::getField)
                .anyMatch(Field::hasPiece);
    }

    private boolean isCastleLegalInRequirements(CastleType castleType) {
        CastleRequirements castleRequirements = chessBoard.getCastleRequirements();
        if (chessBoard.getColor().isWhite()) {
            if (castleType.equals(CastleType.SHORT)) {
                return castleRequirements.canCastleWhiteShort();
            }
            if (castleType.equals(CastleType.LONG)) {
                return castleRequirements.canCastleWhiteLong();
            }
        }
        if (chessBoard.getColor().isBlack()) {
            if (castleType.equals(CastleType.SHORT)) {
                return castleRequirements.canCastleBlackShort();
            }
            if (castleType.equals(CastleType.LONG)) {
                return castleRequirements.canCastleBlackLong();
            }
        }
        throw new IllegalArgumentException("Wrong castle type");
    }

    private Set<Position> positionsKingPasses(CastleType castleType) {
        Set<Position> result = new HashSet<>();
        if (castleType.equals(CastleType.SHORT)) {
            result.add(Position.of(5, getStartRow()));
            result.add(Position.of(6, getStartRow()));
            result.add(Position.of(7, getStartRow()));
        }
        if (castleType.equals(CastleType.LONG)) {
            result.add(Position.of(5, getStartRow()));
            result.add(Position.of(4, getStartRow()));
            result.add(Position.of(3, getStartRow()));
        }
        return result;
    }

    private Set<Position> positionsBetweenKingAndRook(CastleType castleType) {
        Set<Position> result = new HashSet<>();
        if (castleType.equals(CastleType.SHORT)) {
            result.add(Position.of(6, getStartRow()));
            result.add(Position.of(7, getStartRow()));
        }
        if (castleType.equals(CastleType.LONG)) {
            result.add(Position.of(4, getStartRow()));
            result.add(Position.of(3, getStartRow()));
            result.add(Position.of(2, getStartRow()));
        }
        return result;
    }

    private int getStartRow() {
        if (chessBoard.getColor().isWhite()) {
            return 1;
        } else {
            return 8;
        }
    }
}
