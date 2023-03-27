package chess.board.features;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.moves.*;
import chess.pieces.Pawn;
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
                        getAllPossibleSimpleMoves().stream(),
                        getAllPossibleCastles().stream()
                ),
                getAllPossibleEnPassantCaptures().stream()
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
                new RawMove(new Position(5, 8), new Position(7, 8)),
                new RawMove(new Position(5, 8), new Position(3, 8)),
                new RawMove(new Position(5, 1), new Position(7, 1)),
                new RawMove(new Position(5, 1), new Position(3, 1))
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
}
