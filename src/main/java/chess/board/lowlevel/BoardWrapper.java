package chess.board.lowlevel;

import chess.board.ChessBoard;
import chess.board.fields.EmptyField;
import chess.board.fields.Field;
import chess.board.fields.OccupiedField;
import chess.pieces.*;
import chess.Position;
import chess.color.Color;

public class BoardWrapper {
    private final ChessBoard chessBoard;

    public BoardWrapper(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public Field getFieldFromBoard(Position position) {
        return convertIdToField(position, chessBoard.getBoard().read(position));
    }

    public Board putFieldToBoard(Field field) {
        Board tempBoard = Board.getCopy(chessBoard.getBoard());
        if (field.isEmpty()) {
            tempBoard.write(Board.EMPTY, field.getPosition());
        } else {
            tempBoard.write(getBoardIdFromPiece(field.getPiece()), field.getPosition());
        }
        return tempBoard;
    }

    private byte getBoardIdFromPiece(Piece piece) {
        if (piece instanceof Pawn) {
            return piece.getColor().isWhite() ? Board.WPAWN : Board.BPAWN;
        }
        if (piece instanceof Knight) {
            return piece.getColor().isWhite() ? Board.WKNIGHT : Board.BKNIGHT;
        }
        if (piece instanceof Bishop) {
            return piece.getColor().isWhite() ? Board.WBISHOP : Board.BBISHOP;
        }
        if (piece instanceof Rook) {
            return piece.getColor().isWhite() ? Board.WROOK : Board.BROOK;
        }
        if (piece instanceof Queen) {
            return piece.getColor().isWhite() ? Board.WQUEEN : Board.BQUEEN;
        }
        if (piece instanceof King) {
            return piece.getColor().isWhite() ? Board.WKING : Board.BKING;
        }
        throw new IllegalStateException();
    }

    private Field convertIdToField(Position position, int id) {
        if (id == Board.EMPTY) {
            return EmptyField.of(position);
        }
        return new OccupiedField(switch (id) {
            case Board.WPAWN -> new Pawn(Color.white, position, chessBoard);
            case Board.WROOK -> new Rook(Color.white, position, chessBoard);
            case Board.WBISHOP -> new Bishop(Color.white, position, chessBoard);
            case Board.WQUEEN -> new Queen(Color.white, position, chessBoard);
            case Board.WKING -> new King(Color.white, position, chessBoard);
            case Board.WKNIGHT -> new Knight(Color.white, position, chessBoard);
            case Board.BPAWN -> new Pawn(Color.black, position, chessBoard);
            case Board.BROOK -> new Rook(Color.black, position, chessBoard);
            case Board.BBISHOP -> new Bishop(Color.black, position, chessBoard);
            case Board.BQUEEN -> new Queen(Color.black, position, chessBoard);
            case Board.BKING -> new King(Color.black, position, chessBoard);
            case Board.BKNIGHT -> new Knight(Color.black, position, chessBoard);
            default -> throw new IllegalArgumentException("unknown board figure id");
        });
    }
}
