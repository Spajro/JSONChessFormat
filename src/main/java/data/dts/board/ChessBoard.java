package data.dts.board;

import data.dts.Position;
import data.dts.ValidMoveFactory;
import data.dts.color.Color;
import data.dts.exceptions.ChessAxiomViolation;
import data.dts.exceptions.IllegalMoveException;
import data.dts.fields.Field;
import data.dts.moves.RawMove;
import data.dts.moves.ValidMove;
import data.dts.pieces.Piece;

import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChessBoard {
    private final Board board;
    private final Color color;
    private final CastleRequirements castleRequirements;
    private final CastleRequirementsFactory castleRequirementsFactory = new CastleRequirementsFactory(this);
    private final ValidMoveFactory validMoveFactory = new ValidMoveFactory(this);

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
        return BoardWrapper.getFieldFromBoard(this, position);
    }

    public ChessBoard makeMove(RawMove move) throws IllegalMoveException, ChessAxiomViolation {
        ValidMove validMove = validMoveFactory.createValidMove(move);
        return new ChessBoard(validMove.makeMove(), color.swap(), castleRequirementsFactory.getNextRequirements(validMove));
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

    public List<Piece> getPiecesOfColor(Color color) {
        return getAllPositions().stream()
                .map(this::getField)
                .filter(Field::hasPiece)
                .map(Field::getPiece)
                .filter(piece -> piece.getColor().equal(color))
                .toList();
    }

    public static List<Position> getAllPositions() {
        List<Position> result = new LinkedList<>();
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                result.add(new Position(x, y));
            }
        }
        return result;
    }

    public boolean isPositionAttacked(Position position) {
        return getNumberOfPiecesAttackingFields(color).get(position) > 0;
    }

    private int getStartRow() {
        if (color.isWhite()) {
            return 1;
        } else {
            return 8;
        }
    }

    public CastleRequirements getCastleRequirements() {
        return castleRequirements;
    }
}