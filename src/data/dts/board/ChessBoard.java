package data.dts.board;

import data.dts.Move;
import data.dts.Position;
import data.dts.color.Color;
import data.dts.exceptions.ChessAxiomViolation;
import data.dts.fields.Field;
import data.dts.pieces.King;
import data.dts.pieces.Piece;

import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChessBoard {
    private final Board board;
    private final Color color;
    private final CastleRequirements castleRequirements;

    public ChessBoard() {
        board = Board.getStart();
        color = Color.white;
        castleRequirements = new CastleRequirements();
    }

    private ChessBoard(Board board, Color color, CastleRequirements castleRequirements) {
        this.board = board;
        this.color = color;
        this.castleRequirements = castleRequirements;
    }

    @Deprecated
    public int read(Position position) {
        return board.read(position);
    }

    public Field getField(Position position) {
        return BoardWrapper.getFieldFromBoard(this, board, position);
    }

    public ChessBoard makeMove(Move move) {
        try {
            if (isLegal(move)) {
                Board tempBoard = Board.getCopy(board);
                move.makeMove(tempBoard);
                return new ChessBoard(tempBoard, color.swap(), getNextRequirements(move));
            }
        } catch (ChessAxiomViolation e) {
            throw new RuntimeException(e);
        }
        System.out.print("[ChessBoard] -> Move illegal\n");
        return this;
    }

    private boolean isLegal(Move move) throws ChessAxiomViolation {
        if (!Board.getPieceColor(board.read(move.getOldPosition())).equal(color)
                || !move.isCorrect()
                || getField(move.getOldPosition()).isEmpty()) {
            return false;
        }
        if (move.isCastle()) {
            return isCastleLegal(move.getCastle());
        } else
            return getField(move.getOldPosition()).getPiece().getPossibleEndPositions().contains(move.getNewPosition())
                    && (!isKingChecked(color) || (BoardWrapper.getFieldFromBoard(this, board, move.getOldPosition()) instanceof King && !isPositionAttacked(move.getNewPosition())));
    }

    private boolean isCastleLegal(int castle) {
        return switch (castle) {
            case Move.WHITE_SHORT_CASTLE -> castleRequirements.canCastleWhiteShort();
            case Move.WHITE_LONG_CASTLE -> castleRequirements.canCastleWhiteLong();
            case Move.BLACK_SHORT_CASTLE -> castleRequirements.canCastleBlackShort();
            case Move.BLACK_LONG_CASTLE -> castleRequirements.canCastleBlackLong();
            default -> throw new IllegalArgumentException("Wrong castle type");
        };
    }

    private CastleRequirements getNextRequirements(Move move) {
        if (kingMoved(move)) {
            return castleRequirements.kingMoved(color);
        } else if (hColumnRookMoved(move)) {
            return castleRequirements.hColumnRookMoved(color);
        } else if (aColumnRookMoved(move)) {
            return castleRequirements.hColumnRookMoved(color);
        } else {
            return castleRequirements.copy();
        }
    }

    private boolean aColumnRookMoved(Move move) {
        return isRookOnPosition(move.getOldPosition()) && move.getOldPosition().getX() == 1 && move.getOldPosition().getY() == getStartRow();
    }

    private boolean hColumnRookMoved(Move move) {
        return isRookOnPosition(move.getOldPosition()) && move.getOldPosition().getX() == 8 && move.getOldPosition().getY() == getStartRow();
    }

    private boolean kingMoved(Move move) {
        return isKingOnPosition(move.getOldPosition()) && move.getOldPosition().getX() == 5 && move.getOldPosition().getY() == getStartRow();
    }

    private boolean isRookOnPosition(Position position) {
        Field field = BoardWrapper.getFieldFromBoard(this, board, position);
        if (field.isEmpty()) {
            return false;
        }
        Piece piece = field.getPiece();
        return (piece instanceof King) && piece.getColor().equal(color);
    }

    private boolean isKingOnPosition(Position position) {
        Field field = BoardWrapper.getFieldFromBoard(this, board, position);
        if (field.isEmpty()) {
            return false;
        }
        Piece piece = field.getPiece();
        return (piece instanceof King) && piece.getColor().equal(color);
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessBoard that = (ChessBoard) o;

        if (!board.equals(that.board)) return false;
        return color.equals(that.color);
    }

    @Override
    public int hashCode() {
        int result = board.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    public Board getBoard() {
        return board;
    }

    public Map<Position, Long> getNumberOfPiecesAttackingFields(Color color) {
        return getPiecesOfColor(color).stream()
                .map(Piece::getPossibleEndPositions)
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private List<Piece> getPiecesOfColor(Color color) {
        return getAllPositions().stream()
                .map(this::getField)
                .filter(Field::hasPiece)
                .map(Field::getPiece)
                .filter(piece -> piece.getColor().equal(color))
                .toList();
    }

    private List<Position> getAllPositions() {
        List<Position> result = new LinkedList<>();
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                result.add(new Position(x, y));
            }
        }
        return result;
    }

    private boolean isKingChecked(Color color) throws ChessAxiomViolation {
        Optional<Piece> optionalKing = getPiecesOfColor(color).stream()
                .filter(piece -> piece instanceof King)
                .findFirst();
        if (optionalKing.isEmpty()) {
            throw new ChessAxiomViolation("No King on Board");
        }
        King king = (King) optionalKing.get();

        return isPositionAttacked(king.getPosition());
    }

    private boolean isPositionAttacked(Position position) {
        return getNumberOfPiecesAttackingFields(color).get(position) > 0;
    }

    private int getStartRow() {
        if (color.isWhite()) {
            return 1;
        } else {
            return 8;
        }
    }
}
