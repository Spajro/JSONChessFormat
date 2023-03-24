package chess.validation;

import chess.Position;
import chess.board.requirements.CastleRequirements;
import chess.board.ChessBoard;
import chess.board.lowlevel.Field;
import chess.moves.RawMove;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CastleValidator {
    private final ChessBoard chessBoard;
    private final CheckValidator checkValidator;

    public CastleValidator(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        checkValidator = new CheckValidator(chessBoard);
    }

    public boolean isLegalCastle(RawMove move) {
        Optional<MoveValidator.CastleType> optionalCastleType = moveToType(move);
        if (optionalCastleType.isPresent()) {
            MoveValidator.CastleType castleType = optionalCastleType.get();
            return checkValidator.getKingPosition(chessBoard.getColor()).equals(move.getStartPosition())
                    && isCastleLegalInRequirements(castleType)
                    && !anyPositionKingsPassesIsAttacked(castleType)
                    && !anyPositionBetweenKingAndRookIsOccupied(castleType)
                    && !checkValidator.isKingChecked(chessBoard.getColor());
        } else {
            return false;
        }
    }

    public Optional<MoveValidator.CastleType> moveToType(RawMove move) {
        if (move.getStartPosition().equals(checkValidator.getKingPosition(chessBoard.getColor()))) {
            if (move.getEndPosition().getX() == move.getStartPosition().getX() + 2) {
                return Optional.of(MoveValidator.CastleType.SHORT);
            }
            if (move.getEndPosition().getX() == move.getStartPosition().getX() - 2) {
                return Optional.of(MoveValidator.CastleType.LONG);
            }
        }
        return Optional.empty();
    }

    private boolean anyPositionKingsPassesIsAttacked(MoveValidator.CastleType castleType) {
        return positionsKingPasses(castleType).stream()
                .filter(position -> chessBoard.getUtility().isPositionAttacked(position, chessBoard.getColor().swap()))
                .toList()
                .size() > 0;
    }

    private boolean anyPositionBetweenKingAndRookIsOccupied(MoveValidator.CastleType castleType) {
        return positionsBetweenKingAndRook(castleType).stream()
                .map(chessBoard::getField)
                .filter(Field::hasPiece)
                .toList()
                .size() > 0;
    }

    private boolean isCastleLegalInRequirements(MoveValidator.CastleType castleType) {
        CastleRequirements castleRequirements = chessBoard.getCastleRequirements();
        if (chessBoard.getColor().isWhite()) {
            if (castleType.equals(MoveValidator.CastleType.SHORT)) {
                return castleRequirements.canCastleWhiteShort();
            }
            if (castleType.equals(MoveValidator.CastleType.LONG)) {
                return castleRequirements.canCastleWhiteLong();
            }
        }
        if (chessBoard.getColor().isBlack()) {
            if (castleType.equals(MoveValidator.CastleType.SHORT)) {
                return castleRequirements.canCastleBlackShort();
            }
            if (castleType.equals(MoveValidator.CastleType.LONG)) {
                return castleRequirements.canCastleBlackLong();
            }
        }
        throw new IllegalArgumentException("Wrong castle type");
    }

    private Set<Position> positionsKingPasses(MoveValidator.CastleType castleType) {
        Set<Position> result = new HashSet<>();
        if (castleType.equals(MoveValidator.CastleType.SHORT)) {
            result.add(new Position(5, getStartRow()));
            result.add(new Position(6, getStartRow()));
            result.add(new Position(7, getStartRow()));
        }
        if (castleType.equals(MoveValidator.CastleType.LONG)) {
            result.add(new Position(5, getStartRow()));
            result.add(new Position(4, getStartRow()));
            result.add(new Position(3, getStartRow()));
        }
        return result;
    }

    private Set<Position> positionsBetweenKingAndRook(MoveValidator.CastleType castleType) {
        Set<Position> result = new HashSet<>();
        if (castleType.equals(MoveValidator.CastleType.SHORT)) {
            result.add(new Position(6, getStartRow()));
            result.add(new Position(7, getStartRow()));
        }
        if (castleType.equals(MoveValidator.CastleType.LONG)) {
            result.add(new Position(4, getStartRow()));
            result.add(new Position(3, getStartRow()));
            result.add(new Position(2, getStartRow()));
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
