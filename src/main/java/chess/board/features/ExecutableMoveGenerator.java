package chess.board.features;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.moves.raw.RawMove;
import chess.moves.valid.UnTypedPromotion;
import chess.moves.valid.executable.*;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.validation.ValidMoveFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ExecutableMoveGenerator {
    private final Color color;
    private final ChessBoardUtility utility;
    private final ValidMoveFactory validMoveFactory;

    public ExecutableMoveGenerator(ChessBoard chessBoard) {
        this.color = chessBoard.getColor();
        utility = new ChessBoardUtility(chessBoard);
        validMoveFactory = new ValidMoveFactory(chessBoard);
    }

    public List<ExecutableMove> getAllPossibleExecutableMoves() {
        return Stream.concat(
                Stream.concat(
                        Stream.concat(
                                getAllPossibleSimpleMoves().stream(),
                                getAllPossibleCastles().stream()
                        ),
                        getAllPossibleEnPassantCaptures().stream()
                ),
                getAllPossiblePromotions().stream()
        ).toList();
    }

    private List<SimpleMove> getAllPossibleSimpleMoves() {
        return utility.getPiecesOfColor(color).stream()
                .flatMap(piece -> piece.getPossibleEndPositions().stream()
                        .map(position -> new RawMove(piece.getPosition(), position)))
                .map(validMoveFactory::createValidMove)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(object -> object instanceof SimpleMove)
                .map(object -> (SimpleMove) object)
                .toList();
    }

    private List<Castle> getAllPossibleCastles() {
        List<RawMove> legalCastleRawMoves = List.of(
                new RawMove(Position.of(5, 8), Position.of(7, 8)),
                new RawMove(Position.of(5, 8), Position.of(3, 8)),
                new RawMove(Position.of(5, 1), Position.of(7, 1)),
                new RawMove(Position.of(5, 1), Position.of(3, 1))
        );
        return legalCastleRawMoves.stream()
                .map(validMoveFactory::createValidMove)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(object -> object instanceof Castle)
                .map(object -> (Castle) object)
                .toList();
    }

    private List<EnPassantCapture> getAllPossibleEnPassantCaptures() {
        return utility.getPiecesOfColor(color).stream()
                .filter(piece -> piece instanceof Pawn)
                .map(piece -> (Pawn) piece)
                .flatMap(pawn -> pawn.getAttackedPositions().stream().map(position -> new RawMove(pawn.getPosition(), position)))
                .map(validMoveFactory::createValidMove)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(object -> object instanceof EnPassantCapture)
                .map(object -> (EnPassantCapture) object)
                .toList();
    }

    private List<Promotion> getAllPossiblePromotions() {
        return utility.getPiecesOfColor(color).stream()
                .filter(piece -> piece instanceof Pawn)
                .map(piece -> (Pawn) piece)
                .filter(this::isOnPenultimateLine)
                .flatMap(pawn -> pawn.getPossibleEndPositions().stream()
                        .map(position -> new RawMove(pawn.getPosition(), position)))
                .map(validMoveFactory::createValidMove)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(object -> object instanceof UnTypedPromotion)
                .map(object -> (UnTypedPromotion) object)
                .flatMap(this::typePromotion)
                .toList();

    }

    private boolean isOnPenultimateLine(Pawn pawn) {
        if (pawn.getColor().isWhite()) {
            return pawn.getPosition().getY() == 7;
        } else {
            return pawn.getPosition().getY() == 2;
        }
    }

    private Stream<Promotion> typePromotion(UnTypedPromotion promotion) {
        List<Piece.Type> types = List.of(Piece.Type.KNIGHT, Piece.Type.BISHOP, Piece.Type.ROOK, Piece.Type.QUEEN);
        return types.stream().map(promotion::type);
    }
}
