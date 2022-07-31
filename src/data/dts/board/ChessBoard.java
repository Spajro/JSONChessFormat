package data.dts.board;

import data.dts.Move;
import data.dts.Position;
import data.dts.color.Color;
import data.dts.fields.Field;
import data.dts.pieces.Piece;

import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChessBoard {
    private final Board board;
    private final Color color;

    public ChessBoard() {
        board = Board.getStart();
        color = Color.white;
    }

    private ChessBoard(Board board, Color color) {
        this.board = board;
        this.color = color;
    }

    @Deprecated
    public int read(Position position) {
        return board.read(position);
    }

    public Field getField(Position position) {
        return BoardWrapper.getFieldFromBoard(this, board, position);
    }

    public ChessBoard makeMove(Move move) {
        if (isLegal(move)) {
            Board tempBoard = Board.getCopy(board);
            move.makeMove(tempBoard);
            return new ChessBoard(tempBoard, color.swap());
        }
        System.out.print("[ChessBoard] -> Move illegal\n");
        return this;
    }

    private boolean isLegal(Move move) {
        return Board.getPieceColor(board.read(move.getOldPosition())).equal(color)
                && move.isCorrect()
                && !getField(move.getOldPosition()).isEmpty()
                && getField(move.getOldPosition()).getPiece().getPossibleEndPositions().contains(move.getNewPosition());
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
                .toList();
    }

    public List<Position> getAllPositions() {
        List<Position> result = new LinkedList<>();
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                result.add(new Position(x, y));
            }
        }
        return result;
    }
}
