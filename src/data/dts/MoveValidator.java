package data.dts;

import data.dts.board.Board;
import data.dts.board.BoardWrapper;
import data.dts.board.CastleRequirements;
import data.dts.board.ChessBoard;
import data.dts.color.Color;
import data.dts.exceptions.ChessAxiomViolation;
import data.dts.exceptions.IllegalCastleException;
import data.dts.fields.Field;
import data.dts.moves.RawMove;
import data.dts.pieces.King;

import java.util.HashSet;
import java.util.Set;


public class MoveValidator {
    private final ChessBoard chessBoard;

    enum CastleType {
        SHORT, LONG
    }

    public MoveValidator(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public boolean isCorrect(RawMove move) {
        return !Board.getPieceColor(chessBoard.read(move.getStartPosition())).equal(chessBoard.getColor())
                || !move.isEmpty()
                || chessBoard.getField(move.getStartPosition()).isEmpty();
    }

    public boolean isLegalSimpleMove(RawMove move) {
        try {
            return chessBoard.getField(move.getStartPosition()).getPiece().getPossibleEndPositions().contains(move.getEndPosition())
                    && (!isKingChecked(chessBoard.getColor())
                    || (BoardWrapper.getFieldFromBoard(chessBoard,
                    move.getStartPosition()) instanceof King && !chessBoard.isPositionAttacked(move.getEndPosition())));
        } catch (ChessAxiomViolation e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isLegalCastle(RawMove move) throws ChessAxiomViolation {
        try {
            CastleType castleType = moveToType(move);
            if (!getKingPosition(chessBoard.getColor()).equals(move.getStartPosition())) {
                return false;
            }
            if (!isCastleLegalInRequirements(castleType)) {
                return false;
            }
            if (positionsKingPasses(castleType).stream()
                    .filter(chessBoard::isPositionAttacked)
                    .toList()
                    .size() != 0
            ) {
                return false;
            }
            if (positionsBetweenKingAndRook(castleType).stream()
                    .map(chessBoard::getField)
                    .filter(Field::isEmpty)
                    .toList()
                    .size() != 0
            ) {
                return false;
            }
        } catch (IllegalCastleException e) {
            return false;
        }
        return true;

    }

    public CastleType moveToType(RawMove move) throws ChessAxiomViolation, IllegalCastleException {
        if (move.getStartPosition().equals(getKingPosition(chessBoard.getColor()))) {
            if (move.getEndPosition().getX() == move.getStartPosition().getX() + 2) {
                return CastleType.SHORT;
            }
            if (move.getEndPosition().getX() == move.getStartPosition().getX() - 2) {
                return CastleType.LONG;
            }
        }
        throw new IllegalCastleException("Move is not a castle");
    }

    private boolean isKingChecked(Color color) throws ChessAxiomViolation {
        return chessBoard.isPositionAttacked(getKingPosition(color));
    }

    private Position getKingPosition(Color color) throws ChessAxiomViolation {
        return chessBoard.getPiecesOfColor(color).stream()
                .filter(piece -> piece instanceof King)
                .findFirst()
                .orElseThrow(() -> new ChessAxiomViolation("No King on Board"))
                .getPosition();
    }

    private boolean isCastleLegalInRequirements(CastleType castleType) throws IllegalCastleException {
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
            result.add(new Position(5, getStartRow()));
            result.add(new Position(6, getStartRow()));
            result.add(new Position(7, getStartRow()));
        }
        if (castleType.equals(CastleType.LONG)) {
            result.add(new Position(5, getStartRow()));
            result.add(new Position(4, getStartRow()));
            result.add(new Position(3, getStartRow()));
        }
        return result;
    }

    private Set<Position> positionsBetweenKingAndRook(CastleType castleType) {
        Set<Position> result = new HashSet<>();
        if (castleType.equals(CastleType.SHORT)) {
            result.add(new Position(6, getStartRow()));
            result.add(new Position(7, getStartRow()));
        }
        if (castleType.equals(CastleType.LONG)) {
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
