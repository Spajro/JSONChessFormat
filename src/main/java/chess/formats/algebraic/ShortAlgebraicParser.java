package chess.formats.algebraic;

import chess.Position;
import chess.board.ChessBoard;
import chess.color.Color;
import chess.moves.raw.RawMove;
import chess.moves.raw.RawPromotion;
import chess.pieces.*;
import chess.pools.PoolManager;
import chess.validation.MoveValidator;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ShortAlgebraicParser {
    private final AlgebraicUtility utility = AlgebraicUtility.getInstance();

    public RawMove parseShortAlgebraic(String move, ChessBoard chessBoard) {
        return utility.algebraicCastleToMove(removeCheckmarks(move), chessBoard.getColor())
                .orElseGet(() -> shortAlgebraicToMove(removeCheckmarks(move), chessBoard)
                        .orElseThrow(() -> new IllegalArgumentException("Illegal short algebraic: " + move))
                );
    }

    private Optional<RawMove> shortAlgebraicToMove(String move, ChessBoard chessBoard) {
        return switch (move.length()) {
            case 2 -> pieceToMove('P' + move, chessBoard);
            case 3 -> xor(
                    pieceCaptureToMove('P' + move, chessBoard),
                    pieceToMove(move, chessBoard)
            );
            case 4 -> listXor(List.of(
                            pieceCaptureToMove(move, chessBoard),
                            pawnPromotionToMove(move, chessBoard),
                            ambiguousPieceToMove(move, chessBoard),
                            ambiguousPieceCaptureToMove('P' + move, chessBoard)
                    )
            );
            case 5 -> listXor(List.of(
                            pawnCapturePromotionToMove(move, chessBoard),
                            ambiguousPieceCaptureToMove(move, chessBoard),
                            doubleAmbiguousPieceToMove(move, chessBoard)
                    )
            );
            case 6 -> xor(
                    ambiguousPawnCapturePromotionToMove(move, chessBoard),
                    doubleAmbiguousPieceCaptureToMove(move, chessBoard)
            );
            default -> throw new IllegalStateException("Unexpected algebraic length: " + move.length());
        };
    }

    private Optional<RawMove> listXor(List<Optional<RawMove>> list) {
        return list.stream().reduce(Optional.empty(), this::xor);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<RawMove> xor(Optional<RawMove> first, Optional<RawMove> second) {
        if (first.isPresent() && second.isPresent()) {
            throw new IllegalArgumentException();
        } else if (first.isEmpty() && second.isEmpty()) {
            return Optional.empty();
        } else if (first.isPresent()) {
            return first;
        } else {
            return second;
        }
    }

    private Optional<RawMove> pieceToMove(String move, ChessBoard chessBoard) {
        Optional<Position> end = utility.algebraicToPosition(move.substring(1));
        if (end.isEmpty()) {
            return Optional.empty();
        }
        Optional<Piece> optionalPiece = charToPiece(move.charAt(0), end.get(), chessBoard.getColor());
        if (optionalPiece.isEmpty()) {
            return Optional.empty();
        }
        return getSinglePieceMove(optionalPiece.get(), chessBoard);
    }

    private Optional<RawMove> pieceCaptureToMove(String move, ChessBoard chessBoard) {
        if (move.charAt(1) != 'x') {
            return Optional.empty();
        }
        return pieceToMove(move.charAt(0) + move.substring(2), chessBoard);
    }

    private Optional<RawMove> ambiguousPieceToMove(String move, ChessBoard chessBoard) {
        Optional<Position> end = utility.algebraicToPosition(move.substring(2));
        if (end.isEmpty()) {
            return Optional.empty();
        }
        Optional<Piece> optionalPiece = charToPiece(move.charAt(0), end.get(), chessBoard.getColor());
        if (optionalPiece.isEmpty()) {
            return Optional.empty();
        }
        Piece piece = optionalPiece.get();
        Set<Position> positionSet = piece.getPossibleStartPositions(chessBoard);
        if (positionSet.isEmpty()) {
            return Optional.empty();
        }
        Optional<Position> optionalStart = chooseByAmbiguous(positionSet, move.charAt(1));
        return optionalStart.map(position -> RawMove.of(position, end.get()));
    }

    private Optional<RawMove> ambiguousPieceCaptureToMove(String move, ChessBoard chessBoard) {
        if (move.charAt(2) != 'x') {
            return Optional.empty();
        }
        return ambiguousPieceToMove(move.substring(0, 2) + move.substring(3), chessBoard);
    }

    private Optional<RawMove> doubleAmbiguousPieceToMove(String move, ChessBoard chessBoard) {
        Optional<Position> end = utility.algebraicToPosition(move.substring(3));
        if (end.isEmpty()) {
            return Optional.empty();
        }
        Optional<Piece> optionalPiece = charToPiece(move.charAt(0), end.get(), chessBoard.getColor());
        if (optionalPiece.isEmpty()) {
            return Optional.empty();
        }
        Piece piece = optionalPiece.get();
        Set<Position> positionSet = piece.getPossibleStartPositions(chessBoard);
        if (positionSet.isEmpty()) {
            return Optional.empty();
        }
        Optional<Position> optionalStart = chooseByDoubleAmbiguous(positionSet, move.substring(1, 3));
        return optionalStart.map(position -> RawMove.of(position, end.get()));
    }

    private Optional<RawMove> doubleAmbiguousPieceCaptureToMove(String move, ChessBoard chessBoard) {
        if (move.charAt(3) != 'x') {
            return Optional.empty();
        }
        return doubleAmbiguousPieceToMove(move.substring(0, 3) + move.substring(4), chessBoard);
    }

    private Optional<RawMove> pawnPromotionToMove(String move, ChessBoard chessBoard) {
        if (move.charAt(2) != '=') {
            return Optional.empty();
        }

        Optional<RawMove> optionalRawMove = pieceToMove("P" + move.substring(0, 2), chessBoard);
        Optional<Piece.Type> optionalType = utility.algebraicToType(move.charAt(3));
        if (optionalRawMove.isEmpty() || optionalType.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new RawPromotion(optionalRawMove.get(), optionalType.get()));
    }

    private Optional<RawMove> pawnCapturePromotionToMove(String move, ChessBoard chessBoard) {
        if (move.charAt(0) != 'x') {
            return Optional.empty();
        }

        return pawnPromotionToMove(move.substring(1), chessBoard);
    }

    private Optional<RawMove> ambiguousPawnCapturePromotionToMove(String move, ChessBoard chessBoard) {
        if (move.charAt(1) != 'x' || move.charAt(4) != '=') {
            return Optional.empty();
        }

        Optional<RawMove> optionalRawMove = ambiguousPieceCaptureToMove("P" + move.substring(0, 4), chessBoard);
        Optional<Piece.Type> optionalType = utility.algebraicToType(move.charAt(5));
        if (optionalRawMove.isEmpty() || optionalType.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new RawPromotion(optionalRawMove.get(), optionalType.get()));
    }

    private Optional<RawMove> getSinglePieceMove(Piece piece, ChessBoard chessBoard) {
        Set<Position> positionSet = piece.getPossibleStartPositions(chessBoard);
        if (positionSet.size() != 1) {
            Set<Position> filteredSet = positionSet.stream()
                    .filter(position -> new MoveValidator(chessBoard).isLegalSimpleMove(RawMove.of(position, piece.getPosition())))
                    .collect(Collectors.toSet());
            if (filteredSet.size() != 1) {
                return Optional.empty();
            }
            return Optional.of(RawMove.of(filteredSet.stream().findAny().get(), piece.getPosition()));
        }
        return Optional.of(RawMove.of(positionSet.stream().findAny().get(), piece.getPosition()));
    }

    private Optional<Position> chooseByAmbiguous(Set<Position> positions, char ambiguous) {
        Optional<Integer> column = utility.columnToNumber(ambiguous);
        if (column.isPresent()) {
            Set<Position> positionSet = positions.stream()
                    .filter(position -> position.getX() == column.get())
                    .collect(Collectors.toSet());
            if (positionSet.size() != 1) {
                return Optional.empty();
            }
            return positionSet.stream().findAny();
        }

        Optional<Integer> row = rowToNumber(ambiguous);
        if (row.isPresent()) {
            Set<Position> positionSet = positions.stream()
                    .filter(position -> position.getY() == row.get())
                    .collect(Collectors.toSet());
            if (positionSet.size() != 1) {
                return Optional.empty();
            }
            return positionSet.stream().findAny();
        }

        return Optional.empty();
    }

    private Optional<Position> chooseByDoubleAmbiguous(Set<Position> positionSet, String ambiguous) {
        Optional<Position> position = utility.algebraicToPosition(ambiguous);
        if (position.isEmpty()) {
            return Optional.empty();
        }
        if (positionSet.contains(position.get())) {
            return position;
        } else {
            return Optional.empty();
        }
    }

    private String removeCheckmarks(String move) {
        char last = move.charAt(move.length() - 1);
        if (last == '+' || last == '#') {
            return move.substring(0, move.length() - 1);
        } else {
            return move;
        }
    }

    private Optional<Piece> charToPiece(char piece, Position position, Color color) {
        return Optional.ofNullable(switch (piece) {
            case 'P' -> PoolManager.getPiecePool().get(position, color, Piece.Type.PAWN);
            case 'N' -> PoolManager.getPiecePool().get(position, color, Piece.Type.KNIGHT);
            case 'B' -> PoolManager.getPiecePool().get(position, color, Piece.Type.BISHOP);
            case 'R' -> PoolManager.getPiecePool().get(position, color, Piece.Type.ROOK);
            case 'Q' -> PoolManager.getPiecePool().get(position, color, Piece.Type.QUEEN);
            case 'K' -> PoolManager.getPiecePool().get(position, color, Piece.Type.KING);
            default -> null;
        });
    }

    private Optional<Integer> rowToNumber(char row) {
        return Optional.ofNullable(switch (row) {
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            default -> null;
        });
    }
}
