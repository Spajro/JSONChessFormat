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
    private final ChessBoard chessBoard;

    public ExecutableMoveGenerator(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        this.color = chessBoard.getColor();
        utility = new ChessBoardUtility(chessBoard);
        validMoveFactory = new ValidMoveFactory(chessBoard);
    }

    public List<ExecutableMove> getAllPossibleExecutableMoves() {
        List<Piece> pieces = utility.getPiecesOfColor(color);
        return Stream.concat(
                Stream.concat(
                        Stream.concat(
                                getAllPossibleSimpleMoves(pieces).stream(),
                                getAllPossibleCastles().stream()
                        ),
                        getAllPossibleEnPassantCaptures(pieces).stream()
                ),
                getAllPossiblePromotions(pieces).stream()
        ).toList();
    }

    private List<SimpleMove> getAllPossibleSimpleMoves(List<Piece> pieces) {
        return pieces.stream()
                .flatMap(piece -> piece.getPossibleEndPositions(chessBoard).stream()
                        .map(position -> RawMove.of(piece.getPosition(), position)))
                .map(validMoveFactory::createValidMove)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(object -> object instanceof SimpleMove)
                .map(object -> (SimpleMove) object)
                .toList();
    }

    private List<Castle> getAllPossibleCastles() {
        List<RawMove> legalCastleRawMoves = List.of(
                RawMove.of(Position.of(5, 8), Position.of(7, 8)),
                RawMove.of(Position.of(5, 8), Position.of(3, 8)),
                RawMove.of(Position.of(5, 1), Position.of(7, 1)),
                RawMove.of(Position.of(5, 1), Position.of(3, 1))
        );
        return legalCastleRawMoves.stream()
                .map(validMoveFactory::createValidMove)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(object -> object instanceof Castle)
                .map(object -> (Castle) object)
                .toList();
    }

    private List<EnPassantCapture> getAllPossibleEnPassantCaptures(List<Piece> pieces) {
        return pieces.stream()
                .filter(piece -> piece instanceof Pawn)
                .map(piece -> (Pawn) piece)
                .flatMap(pawn -> pawn.getAttackedPositions(chessBoard).stream().map(position -> RawMove.of(pawn.getPosition(), position)))
                .map(validMoveFactory::createValidMove)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(object -> object instanceof EnPassantCapture)
                .map(object -> (EnPassantCapture) object)
                .toList();
    }

    private List<Promotion> getAllPossiblePromotions(List<Piece> pieces) {
        return pieces.stream()
                .filter(piece -> piece instanceof Pawn)
                .map(piece -> (Pawn) piece)
                .filter(this::isOnPenultimateLine)
                .flatMap(pawn -> pawn.getPossibleEndPositions(chessBoard).stream()
                        .map(position -> RawMove.of(pawn.getPosition(), position)))
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
