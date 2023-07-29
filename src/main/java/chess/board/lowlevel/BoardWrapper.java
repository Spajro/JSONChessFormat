package chess.board.lowlevel;

import chess.board.ChessBoard;
import chess.board.fields.EmptyField;
import chess.board.fields.Field;
import chess.board.fields.OccupiedField;
import chess.pieces.*;
import chess.Position;
import chess.color.Color;
import chess.pools.PoolManager;

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
            case Board.WPAWN -> PoolManager.getPiecePool().get(position, Color.white, Piece.Type.PAWN);
            case Board.WROOK -> PoolManager.getPiecePool().get(position, Color.white, Piece.Type.ROOK);
            case Board.WBISHOP -> PoolManager.getPiecePool().get(position, Color.white, Piece.Type.BISHOP);
            case Board.WQUEEN -> PoolManager.getPiecePool().get(position, Color.white, Piece.Type.QUEEN);
            case Board.WKING -> PoolManager.getPiecePool().get(position, Color.white, Piece.Type.KING);
            case Board.WKNIGHT -> PoolManager.getPiecePool().get(position, Color.white, Piece.Type.KNIGHT);
            case Board.BPAWN -> PoolManager.getPiecePool().get(position, Color.black, Piece.Type.PAWN);
            case Board.BROOK -> PoolManager.getPiecePool().get(position, Color.black, Piece.Type.ROOK);
            case Board.BBISHOP -> PoolManager.getPiecePool().get(position, Color.black, Piece.Type.BISHOP);
            case Board.BQUEEN -> PoolManager.getPiecePool().get(position, Color.black, Piece.Type.QUEEN);
            case Board.BKING -> PoolManager.getPiecePool().get(position, Color.black, Piece.Type.KING);
            case Board.BKNIGHT -> PoolManager.getPiecePool().get(position, Color.black, Piece.Type.KNIGHT);
            default -> throw new IllegalArgumentException("unknown board figure id");
        });
    }
}
