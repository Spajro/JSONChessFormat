package chess.board;

import chess.board.features.ChessBoardUtility;
import chess.board.features.ValidMoveGenerator;
import chess.board.lowlevel.Board;
import chess.board.lowlevel.BoardWrapper;
import chess.board.lowlevel.Field;
import chess.board.requirements.CastleRequirements;
import chess.board.requirements.CastleRequirementsFactory;
import chess.exceptions.ChessAxiomViolation;
import chess.exceptions.IllegalMoveException;
import chess.Position;
import chess.results.InvalidMoveResult;
import chess.results.MoveResult;
import chess.results.ValidMoveResult;
import chess.validation.ValidMoveFactory;
import chess.color.Color;
import chess.moves.RawMove;
import chess.moves.ValidMove;
import chess.pieces.Piece;

import java.util.*;

public class ChessBoard {
    private final Board board;
    private final Color color;
    private final CastleRequirements castleRequirements;
    private final ValidMove lastMove;
    private final BoardWrapper boardWrapper = new BoardWrapper(this);
    private final ChessBoardUtility utility = new ChessBoardUtility(this);
    private final CastleRequirementsFactory castleRequirementsFactory = new CastleRequirementsFactory(this);
    private final ValidMoveFactory validMoveFactory = new ValidMoveFactory(this);
    private final ValidMoveGenerator generator;

    public ChessBoard() {
        board = Board.getStart();
        color = Color.white;
        castleRequirements = new CastleRequirements();
        lastMove = null;
        generator = new ValidMoveGenerator(this);
    }

    private ChessBoard(Board board, Color color, CastleRequirements castleRequirements, ValidMove moveCreatingBoard) {
        this.board = board;
        this.color = color;
        this.castleRequirements = castleRequirements;
        this.lastMove = moveCreatingBoard;
        generator = new ValidMoveGenerator(this);
    }

    public static ChessBoard getBlank(Color color) {
        return new ChessBoard(Board.getBlank(), color, new CastleRequirements(), null);
    }

    public ChessBoard put(Piece piece) {
        if (getField(piece.getPosition()).isEmpty()) {
            return new ChessBoard(boardWrapper.putFieldToBoard(new Field(piece.getPosition(), piece)), color, castleRequirements, lastMove);
        }
        throw new IllegalArgumentException("Cant put to board");
    }

    public Field getField(Position position) {
        return boardWrapper.getFieldFromBoard(position);
    }

    public MoveResult makeMove(RawMove move) {
        try {
            ValidMove validMove = validMoveFactory.createValidMove(move);
            return new ValidMoveResult(new ChessBoard(validMove.makeMove(), color.swap(), castleRequirementsFactory.getNextRequirements(validMove), validMove));
        } catch (IllegalMoveException e) {
            return new InvalidMoveResult();
        } catch (ChessAxiomViolation e) {
            throw new RuntimeException(e);
        }
    }

    public ChessBoard makeMove(ValidMove validMove) {
        return new ChessBoard(validMove.makeMove(), color.swap(), castleRequirementsFactory.getNextRequirements(validMove), validMove);
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

    public CastleRequirements getCastleRequirements() {
        return castleRequirements;
    }

    public Optional<ValidMove> getLastMove() {
        return Optional.ofNullable(lastMove);
    }

    public ChessBoardUtility getUtility() {
        return utility;
    }

    public ValidMoveGenerator getGenerator() {
        return generator;
    }
}
