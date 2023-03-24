package chess.utility;

import chess.Position;
import chess.board.ChessBoard;
import chess.board.lowlevel.Board;
import chess.board.requirements.CastleRequirements;
import chess.color.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class FENTranslator {

    public ChessBoard parseFEN(String fen) {
        String[] splitted = fen.split(" ");
        if (splitted.length < 4) {
            throw new IllegalArgumentException("FEN too short");
        }
        Board board = parsePieces(splitted[0]);
        Color moveColor = parseColor(splitted[1]);
        CastleRequirements castleRequirements = parseCastles(splitted[2]);

        return new ChessBoard(board, moveColor, castleRequirements, null);
    }

    private Board parsePieces(String pieces) {
        String[] splitted = pieces.split("/");
        List<List<Integer>> expanded = Arrays.stream(splitted)
                .map(this::expandNumbers)
                .toList();
        Board board = Board.getBlank();
        for (int y = 1; y <= Board.SIZE; y++) {
            for (int x = 1; x <= Board.SIZE; x++) {
                board.write(asciiToByte(expanded.get(8 - y).get(x - 1)), new Position(x, y));
            }
        }
        return board;
    }

    private List<Integer> expandNumbers(String row) {
        return row.chars().flatMap(c -> {
                    if (c > 49 && c < 58) {
                        return Collections.nCopies(c - 48, 49).stream().mapToInt(i -> i);
                    } else {
                        return IntStream.of(c);
                    }
                })
                .boxed()
                .toList();
    }

    private Color parseColor(String color) {
        if (color.equals("w")) {
            return Color.white;
        } else if (color.equals("b")) {
            return Color.black;
        } else {
            throw new IllegalArgumentException(color + " is not a valid color");
        }
    }

    private CastleRequirements parseCastles(String castles) {
        if (castles.equals("-")) {
            return new CastleRequirements(false, false, false, false);
        } else {
            return new CastleRequirements(
                    castles.contains("K"),
                    castles.contains("Q"),
                    castles.contains("k"),
                    castles.contains("q")
            );
        }
    }

    private byte asciiToByte(int ascii) {
        return switch (ascii) {
            case 49 -> Board.EMPTY;
            case 80 -> Board.WPAWN;
            case 82 -> Board.WROOK;
            case 78 -> Board.WKNIGHT;
            case 66 -> Board.WBISHOP;
            case 81 -> Board.WQUEEN;
            case 75 -> Board.WKING;
            case 112 -> Board.BPAWN;
            case 114 -> Board.BROOK;
            case 110 -> Board.BKNIGHT;
            case 98 -> Board.BBISHOP;
            case 113 -> Board.BQUEEN;
            case 107 -> Board.BKING;
            default -> throw new IllegalStateException("Unexpected value: " + ascii);
        };
    }
}
