package chess.board;

import chess.board.features.ChessBoardUtility;
import chess.board.features.ExecutableMoveGenerator;
import chess.board.lowlevel.Board;
import chess.board.lowlevel.BoardWrapper;
import chess.board.lowlevel.Field;
import chess.board.requirements.CastleRequirements;
import chess.board.requirements.CastleRequirementsFactory;
import chess.Position;
import chess.moves.valid.executable.ExecutableMove;
import chess.moves.valid.UnTypedPromotion;
import chess.results.InvalidMoveResult;
import chess.results.MoveResult;
import chess.results.PromotionResult;
import chess.results.ValidMoveResult;
import chess.validation.ValidMoveFactory;
import chess.color.Color;
import chess.moves.raw.RawMove;
import chess.moves.valid.ValidMove;
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
    private final Position whiteKingPosition;
    private final Position blackKingPosition;

    public ChessBoard() {
        board = Board.getStart();
        color = Color.white;
        castleRequirements = new CastleRequirements();
        lastMove = null;
        generator = new ExecutableMoveGenerator(this);
        whiteKingPosition = Position.of(5, 1);
        blackKingPosition = Position.of(5, 8);
    }

    public ChessBoard(Board board,
                      Color color,
                      CastleRequirements castleRequirements,
                      ValidMove moveCreatingBoard,
                      Position whiteKingPosition,
                      Position blackKingPosition) {
        this.board = board;
        this.color = color;
        this.castleRequirements = castleRequirements;
        this.lastMove = moveCreatingBoard;
        generator = new ExecutableMoveGenerator(this);
        this.whiteKingPosition = whiteKingPosition;
        this.blackKingPosition = blackKingPosition;
    }

    public static ChessBoard getBlank(Color color) {
        return new ChessBoard(
                Board.getBlank(),
                color,
                new CastleRequirements(),
                null,
                null,
                null);
    }

    public ChessBoard put(Piece piece) {
        if (getField(piece.getPosition()).hasPiece()) {
            throw new IllegalArgumentException("Cant put to board");
        }

        Position nextWhiteKingPosition = whiteKingPosition;
        Position nextBlackKingPosition = blackKingPosition;
        if (piece.getType().equals(Piece.Type.KING)) {
            if (piece.getColor().isWhite()) {
                if (whiteKingPosition != null) {
                    throw new IllegalArgumentException("Cant put second king to board");
                }
                nextWhiteKingPosition = piece.getPosition();
            } else {
                if (blackKingPosition != null) {
                    throw new IllegalArgumentException("Cant put second king to board");
                }
                nextBlackKingPosition = piece.getPosition();
            }
        }

        return new ChessBoard(
                boardWrapper.putFieldToBoard(new Field(piece.getPosition(), piece)),
                color,
                castleRequirements,
                lastMove,
                nextWhiteKingPosition,
                nextBlackKingPosition);
    }

    public Field getField(Position position) {
        return boardWrapper.getFieldFromBoard(position);
    }

    public MoveResult makeMove(RawMove move) {
        Optional<ValidMove> optionalValidMove = validMoveFactory.createValidMove(move);
        if (optionalValidMove.isPresent()) {
            ValidMove validMove = optionalValidMove.get();
            if (validMove.isExecutable()) {
                ExecutableMove executableMove = (ExecutableMove) validMove;
                Position nextWhiteKingPosition = whiteKingPosition;
                Position nextBlackKingPosition = blackKingPosition;
                if (executableMove.getRepresentation().getStartPosition().equals(whiteKingPosition)) {
                    nextWhiteKingPosition = executableMove.getRepresentation().getEndPosition();
                }
                if (executableMove.getRepresentation().getStartPosition().equals(blackKingPosition)) {
                    nextBlackKingPosition = executableMove.getRepresentation().getEndPosition();
                }
                return new ValidMoveResult(
                        new ChessBoard(
                                executableMove.makeMove(),
                                color.swap(),
                                castleRequirementsFactory.getNextRequirements(validMove),
                                validMove,
                                nextWhiteKingPosition,
                                nextBlackKingPosition),
                        executableMove);
            }
            if (validMove instanceof UnTypedPromotion promotion) {
                return new PromotionResult(this, promotion);
            }
        }
        return new InvalidMoveResult(move);
    }

    public ChessBoard makeMove(ExecutableMove executableMove) {
        Position nextWhiteKingPosition = whiteKingPosition;
        Position nextBlackKingPosition = blackKingPosition;
        if (executableMove.getRepresentation().getStartPosition().equals(whiteKingPosition)) {
            nextWhiteKingPosition = executableMove.getRepresentation().getEndPosition();
        }
        if (executableMove.getRepresentation().getStartPosition().equals(blackKingPosition)) {
            nextBlackKingPosition = executableMove.getRepresentation().getEndPosition();
        }
        return new ChessBoard(
                executableMove.makeMove(),
                color.swap(),
                castleRequirementsFactory.getNextRequirements(executableMove),
                executableMove,
                nextWhiteKingPosition,
                nextBlackKingPosition);
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

    public Position getKingPosition(Color color) {
        if (color.isWhite()) {
            return whiteKingPosition;
        } else {
            return blackKingPosition;
        }
    }
}
