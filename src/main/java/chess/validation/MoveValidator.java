package chess.validation;

import chess.Position;
import chess.board.BoardWrapper;
import chess.board.CastleRequirements;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.exceptions.ChessAxiomViolation;
import chess.exceptions.IllegalCastleException;
import chess.fields.Field;
import chess.moves.RawMove;
import chess.moves.SimpleMove;
import chess.moves.ValidMove;
import chess.pieces.King;
import chess.pieces.Pawn;

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
        return chessBoard.getField(move.getStartPosition()).hasPiece()
                && chessBoard.getField(move.getStartPosition()).getPiece().getColor().equal(chessBoard.getColor());
    }

    public boolean isLegalSimpleMove(RawMove move) {
        try {
            return chessBoard.getField(move.getStartPosition()).getPiece().getPossibleEndPositions().contains(move.getEndPosition())
                    && (!isKingChecked(chessBoard.getColor()) || isKingEscapingFromCheck(move));
        } catch (ChessAxiomViolation e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isLegalEnPassantCapture(RawMove move) {
        if (chessBoard.getLastMove().isPresent()) {
            ValidMove lastMove = chessBoard.getLastMove().get();
            if (lastMove instanceof SimpleMove simpleMove) {
                if (chessBoard.getField(simpleMove.getEndPosition()).getPiece() instanceof Pawn) {
                    if (isFarPawnMove(simpleMove) && simpleMove.getStartPosition().getX() == move.getEndPosition().getX()) {
                        return (move.getEndPosition().getY() == 6 && simpleMove.getStartPosition().getY() == 7)
                                || (move.getEndPosition().getY() == 3 && simpleMove.getStartPosition().getY() == 2);
                    }
                }
            }
        }
        return false;
    }

    private boolean isFarPawnMove(SimpleMove simpleMove) {
        return (simpleMove.getStartPosition().getY() == 2 && simpleMove.getEndPosition().getY() == 4)
                || (simpleMove.getStartPosition().getY() == 7 && simpleMove.getEndPosition().getY() == 5);
    }

    public boolean isLegalCastle(RawMove move) throws ChessAxiomViolation {
        try {
            CastleType castleType = moveToType(move);
            if (!getKingPosition(chessBoard.getColor()).equals(move.getStartPosition())
                    || !isCastleLegalInRequirements(castleType)
                    || anyPositionKingsPassesIsAttacked(castleType)
                    || anyPositionBetweenKingAndRookIsOccupied(castleType)) {
                return false;
            }
        } catch (IllegalCastleException e) {
            return false;
        }
        return true;
    }

    private boolean anyPositionKingsPassesIsAttacked(CastleType castleType) {
        return positionsKingPasses(castleType).stream()
                .filter(chessBoard::isPositionAttacked)
                .toList()
                .size() > 0;
    }

    private boolean anyPositionBetweenKingAndRookIsOccupied(CastleType castleType) {
        return positionsBetweenKingAndRook(castleType).stream()
                .map(chessBoard::getField)
                .filter(Field::isEmpty)
                .toList()
                .size() > 0;
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

    private boolean isKingEscapingFromCheck(RawMove move) {
        return BoardWrapper.getFieldFromBoard(chessBoard, move.getStartPosition()) instanceof King
                && !chessBoard.isPositionAttacked(move.getEndPosition());
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
