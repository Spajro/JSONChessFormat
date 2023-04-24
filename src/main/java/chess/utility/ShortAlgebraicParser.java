package chess.utility;

import chess.Position;
import chess.board.ChessBoard;
import chess.board.lowlevel.Field;
import chess.moves.RawMove;
import chess.pieces.*;
import chess.validation.MoveValidator;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ShortAlgebraicParser {
    private final AlgebraicUtility utility = new AlgebraicUtility();

    public RawMove parseShortAlgebraic(String move, ChessBoard chessBoard) {
        return utility.algebraicCastleToMove(move, chessBoard.getColor())
                .orElseGet(() -> shortAlgebraicToMove(removeCheckmarks(move), chessBoard)
                        .orElseThrow(() -> new IllegalArgumentException("Illegal short algebraic: " + move))
                );
    }

    private Optional<RawMove> shortAlgebraicToMove(String move, ChessBoard chessBoard) {
        return switch (move.length()) {
            case 2 -> pieceToMove('P' + move, chessBoard);
            case 3 -> xor(
                    pieceCaptureToMove('P' + move, chessBoard),
                    pieceToMove(move, chessBoard));
            case 4 -> xor(
                    pieceCaptureToMove(move, chessBoard),
                    xor(
                            ambiguousPieceToMove(move, chessBoard),
                            ambiguousPieceCaptureToMove('P' + move, chessBoard))
            );
            case 5 -> ambiguousPieceCaptureToMove(move, chessBoard);
            default -> throw new IllegalStateException("Unexpected algebraic length: " + move.length());
        };
    }

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
        Position end = utility.algebraicToPosition(move.substring(1));
        Optional<Piece> optionalPiece = charToPiece(move.charAt(0), end, chessBoard);
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
        Position end = utility.algebraicToPosition(move.substring(2));
        Optional<Piece> optionalPiece = charToPiece(move.charAt(0), end, chessBoard);
        if (optionalPiece.isEmpty()) {
            return Optional.empty();
        }
        Piece piece = optionalPiece.get();
        Set<Position> positionSet = getPositions(piece, chessBoard);
        if (positionSet.size() < 1) {
            return Optional.empty();
        }
        Optional<Position> optionalStart = chooseByAmbiguous(positionSet, move.charAt(1));
        if (optionalStart.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new RawMove(optionalStart.get(), end));
    }

    private Optional<RawMove> ambiguousPieceCaptureToMove(String move, ChessBoard chessBoard) {
        if (move.charAt(2) != 'x') {
            return Optional.empty();
        }
        return ambiguousPieceToMove(move.substring(0, 2) + move.substring(3), chessBoard);
    }

    private Optional<RawMove> getSinglePieceMove(Piece piece, ChessBoard chessBoard) {
        Set<Position> positionSet = getPositions(piece, chessBoard);
        if (positionSet.size() != 1) {
            Set<Position> filteredSet = positionSet.stream()
                    .filter(position -> new MoveValidator(chessBoard).isLegalSimpleMove(new RawMove(position, piece.getPosition())))
                    .collect(Collectors.toSet());
            if (filteredSet.size() != 1) {
                return Optional.empty();
            }
            return Optional.of(new RawMove(filteredSet.stream().findAny().get(), piece.getPosition()));
        }
        return Optional.of(new RawMove(positionSet.stream().findAny().get(), piece.getPosition()));
    }

    private Optional<Position> chooseByAmbiguous(Set<Position> positions, char ambiguous) {
        try {
            int column = utility.columnToNumber(ambiguous);
            Set<Position> positionSet = positions.stream()
                    .filter(position -> position.getX() == column)
                    .collect(Collectors.toSet());
            if (positionSet.size() != 1) {
                return Optional.empty();
            }
            return positionSet.stream().findAny();
        } catch (IllegalArgumentException ignored) {
        }
        try {
            int row = rowToNumber(ambiguous);
            Set<Position> positionSet = positions.stream()
                    .filter(position -> position.getY() == row)
                    .collect(Collectors.toSet());
            if (positionSet.size() != 1) {
                return Optional.empty();
            }
            return positionSet.stream().findAny();
        } catch (IllegalArgumentException ignored) {
        }
        return Optional.empty();
    }

    private String removeCheckmarks(String move) {
        char last = move.charAt(move.length() - 1);
        if (last == '+' || last == '#') {
            return move.substring(0, move.length() - 1);
        } else {
            return move;
        }
    }

    private Set<Position> getPositions(Piece piece, ChessBoard chessBoard) {
        return piece
                .getPossibleStartPositions()
                .stream()
                .map(chessBoard::getField)
                .filter(Field::hasPiece)
                .map(Field::getPiece)
                .filter(piece::partiallyEquals)
                .filter(p -> p.getPossibleEndPositions().contains(piece.getPosition()))
                .map(Piece::getPosition)
                .collect(Collectors.toSet());
    }

    private Optional<Piece> charToPiece(char piece, Position position, ChessBoard chessBoard) {
        return Optional.ofNullable(switch (piece) {
            case 'P' -> new Pawn(chessBoard.getColor(), position, chessBoard);
            case 'N' -> new Knight(chessBoard.getColor(), position, chessBoard);
            case 'B' -> new Bishop(chessBoard.getColor(), position, chessBoard);
            case 'R' -> new Rook(chessBoard.getColor(), position, chessBoard);
            case 'Q' -> new Queen(chessBoard.getColor(), position, chessBoard);
            case 'K' -> new King(chessBoard.getColor(), position, chessBoard);
            default -> null;
        });
    }

    private int rowToNumber(char row) {
        return switch (row) {
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            default -> throw new IllegalArgumentException("Not valid row:" + row);
        };
    }
}
