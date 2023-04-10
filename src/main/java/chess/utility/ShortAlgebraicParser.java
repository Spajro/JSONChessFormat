package chess.utility;

import chess.Position;
import chess.board.ChessBoard;
import chess.board.lowlevel.Field;
import chess.moves.RawMove;
import chess.pieces.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ShortAlgebraicParser {
    private final AlgebraicParserUtility utility = new AlgebraicParserUtility();

    public RawMove parseShortAlgebraic(String move, ChessBoard chessBoard) {
        return utility.algebraicCastleToMove(move, chessBoard.getColor())
                .orElseGet(() -> shortAlgebraicToMove(move, chessBoard)
                        .orElseThrow(() -> new IllegalArgumentException("Illegal short algebraic: " + move))
                );
    }

    private Optional<RawMove> shortAlgebraicToMove(String move, ChessBoard chessBoard) {
        return switch (move.length()) {
            case 2 -> pawnToMove(move, chessBoard);
            case 3 -> xor(
                    pawnCaptureToMove(move, chessBoard),
                    xor(
                            specialPawnToMove(move, chessBoard),
                            pieceToMove(move, chessBoard)));
            case 4 -> xor(
                    pieceCaptureToMove(move, chessBoard),
                    xor(
                            specialPieceToMove(move, chessBoard),
                            specialPawnCaptureToMove(move, chessBoard))
            );
            case 5 -> specialPieceCaptureToMove(move, chessBoard);
            default -> throw new IllegalStateException("Unexpected algebraic length: " + move.length());
        };
    }

    private Optional<RawMove> xor(Optional<RawMove> first, Optional<RawMove> second) {
        if (first.isPresent() && second.isEmpty()) {
            return first;
        } else if (first.isEmpty() && second.isPresent()) {
            return second;
        } else if (first.isEmpty() && second.isEmpty()) {
            return Optional.empty();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Optional<RawMove> pawnToMove(String move, ChessBoard chessBoard) {
        Position end = utility.algebraicToPosition(move);
        return getSinglePieceMove(new Pawn(chessBoard.getColor(), end, chessBoard), chessBoard);
    }

    private Optional<RawMove> pawnCaptureToMove(String move, ChessBoard chessBoard) {
        if (move.charAt(0) != 'x') {
            return Optional.empty();
        }
        return pawnToMove(move.substring(1), chessBoard);
    }

    private Optional<RawMove> pieceToMove(String move, ChessBoard chessBoard) {
        Position end = utility.algebraicToPosition(move.substring(1));
        Optional<Piece> optionalPiece = charToPiece(move.charAt(0), end, chessBoard);
        if (optionalPiece.isEmpty()) {
            return Optional.empty();
        }
        return getSinglePieceMove(optionalPiece.get(), chessBoard);
    }

    private Optional<RawMove> specialPawnToMove(String move, ChessBoard chessBoard) {
        Position end = utility.algebraicToPosition(move.substring(1));
        Piece pawn = new Pawn(chessBoard.getColor(), end, chessBoard);
        Set<Position> positionSet = getPositions(pawn, chessBoard);
        if (positionSet.size() < 1) {
            return Optional.empty();
        }
        Optional<Position>optionalStart=chooseBySpecial(positionSet, move.charAt(0));
        if(optionalStart.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new RawMove(optionalStart.get(), end));
    }

    private Optional<RawMove> pieceCaptureToMove(String move, ChessBoard chessBoard) {
        if (move.charAt(1) != 'x') {
            return Optional.empty();
        }
        return pieceToMove(move.charAt(0) + move.substring(2), chessBoard);
    }

    private Optional<RawMove> specialPieceToMove(String move, ChessBoard chessBoard) {
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
        Optional<Position>optionalStart=chooseBySpecial(positionSet, move.charAt(0));
        if(optionalStart.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new RawMove(optionalStart.get(), end));
    }

    private Optional<RawMove> specialPawnCaptureToMove(String move, ChessBoard chessBoard) {
        if (move.charAt(1) != 'x') {
            return Optional.empty();
        }
        return specialPieceToMove(move.charAt(0) + move.substring(2), chessBoard);
    }

    private Optional<RawMove> specialPieceCaptureToMove(String move, ChessBoard chessBoard) {
        if (move.charAt(2) != 'x') {
            return Optional.empty();
        }
        return specialPieceToMove(move.substring(0, 2) + move.substring(3), chessBoard);
    }

    private Optional<RawMove> getSinglePieceMove(Piece piece, ChessBoard chessBoard) {
        Set<Position> positionSet = getPositions(piece, chessBoard);
        if (positionSet.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(new RawMove(positionSet.stream().findAny().get(), piece.getPosition()));
    }

    private Optional<Position> chooseBySpecial(Set<Position> positions, char special) {
        try {
            int column = utility.columnToNumber(special);
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
            int row = rowToNumber(special);
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

    private static Set<Position> getPositions(Piece piece, ChessBoard chessBoard) {
        return piece
                .getPossibleStartPositions()
                .stream()
                .map(chessBoard::getField)
                .filter(Field::hasPiece)
                .map(Field::getPiece)
                .filter(piece::partiallyEquals)
                .map(Piece::getPosition)
                .collect(Collectors.toSet());
    }

    private Optional<Piece> charToPiece(char piece, Position position, ChessBoard chessBoard) {
        return Optional.ofNullable(switch (piece) {
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
