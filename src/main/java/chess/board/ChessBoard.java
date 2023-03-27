package chess.board;

import chess.board.features.ChessBoardUtility;
import chess.board.features.ExecutableMoveGenerator;
import chess.board.lowlevel.Board;
import chess.board.lowlevel.BoardWrapper;
import chess.board.lowlevel.Field;
import chess.board.requirements.CastleRequirements;
import chess.board.requirements.CastleRequirementsFactory;
import chess.Position;
import chess.moves.ExecutableMove;
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
    private final ExecutableMoveGenerator generator;

    public ChessBoard() {
        board = Board.getStart();
        color = Color.white;
        castleRequirements = new CastleRequirements();
        lastMove = null;
        generator = new ExecutableMoveGenerator(this);
    }

    public ChessBoard(Board board, Color color, CastleRequirements castleRequirements, ValidMove moveCreatingBoard) {
        this.board = board;
        this.color = color;
        this.castleRequirements = castleRequirements;
        this.lastMove = moveCreatingBoard;
        generator = new ExecutableMoveGenerator(this);
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
        Optional<ValidMove> optionalValidMove = validMoveFactory.createValidMove(move);
        if (optionalValidMove.isPresent()) {
            ValidMove validMove = optionalValidMove.get();
            if (validMove.isExecutable()) {
                ExecutableMove executableMove= (ExecutableMove) validMove;
                return new ValidMoveResult(
                        new ChessBoard(executableMove.makeMove(),
                                color.swap(),
                                castleRequirementsFactory.getNextRequirements(validMove),
                                validMove)
                );
            }
        }
        return new InvalidMoveResult();
    }

    public ChessBoard makeMove(ExecutableMove validMove) {
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

        return board.equals(that.board) && color.equal(that.color) && castleRequirements.equals(that.castleRequirements);
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

    public ExecutableMoveGenerator getGenerator() {
        return generator;
    }
}
