package chess.utility;

import chess.Position;
import chess.board.ChessBoard;
import chess.board.lowlevel.Board;
import chess.board.requirements.CastleRequirements;
import chess.color.Color;

import java.util.ArrayDeque;

public class FENFactory {
    private static final FENFactory factory = new FENFactory();

    public static FENFactory getInstance() {
        return factory;
    }

    private FENFactory() {
    }

    public String chessBoardToFEN(ChessBoard chessBoard) {
        return boardTOFEN(chessBoard.getBoard()) +
                ' ' +
                colorToChar(chessBoard.getColor()) +
                ' ' +
                castleRequirementsToString(chessBoard.getCastleRequirements()) +
                " -";
    }

    private String boardTOFEN(Board board) {
        StringBuilder result = new StringBuilder();
        for (int y = Board.SIZE; y > 0; y--) {
            ArrayDeque<Integer> row = new ArrayDeque<>();
            for (int x = 1; x <= Board.SIZE; x++) {
                int next = byteToAscii(board.read(Position.of(x, y)));

                if (isAsciiDigit(next) && !row.isEmpty()) {
                    if (isAsciiDigit(row.getLast())) {
                        int last = row.pollLast();
                        row.add(addAsciiDigits(last, next));
                    } else {
                        row.add(next);
                    }
                } else {
                    row.add(next);
                }
            }
            row.forEach(i -> result.append((char) i.intValue()));
            if (y != 1) {
                result.append("/");
            }

        }
        return result.toString();
    }

    private char colorToChar(Color color) {
        if (color.isWhite()) {
            return 'w';
        } else {
            return 'b';
        }
    }

    private String castleRequirementsToString(CastleRequirements requirements) {
        StringBuilder result = new StringBuilder();
        if (!requirements.canCastleWhiteShort()
                && !requirements.canCastleWhiteLong()
                && !requirements.canCastleBlackShort()
                && !requirements.canCastleBlackLong()) {
            result.append('-');
        } else {
            if (requirements.canCastleWhiteShort()) {
                result.append('K');
            }
            if (requirements.canCastleWhiteLong()) {
                result.append("Q");
            }
            if (requirements.canCastleBlackShort()) {
                result.append('k');
            }
            if (requirements.canCastleBlackLong()) {
                result.append("q");
            }
        }
        return result.toString();
    }

    private boolean isAsciiDigit(int ascii) {
        return ascii >= 48 && ascii < 58;
    }

    private int addAsciiDigits(int first, int second) {
        if (isAsciiDigit(first) && isAsciiDigit(second)) {
            int result = first + second - 48;
            if (isAsciiDigit(result)) {
                return result;
            }
        }
        throw new IllegalArgumentException("Illegal ascii add: " + first + " + " + second);
    }

    private int byteToAscii(byte piece) {
        return switch (piece) {
            case Board.EMPTY -> 49;
            case Board.WPAWN -> 80;
            case Board.WROOK -> 82;
            case Board.WKNIGHT -> 78;
            case Board.WBISHOP -> 66;
            case Board.WQUEEN -> 81;
            case Board.WKING -> 75;
            case Board.BPAWN -> 112;
            case Board.BROOK -> 114;
            case Board.BKNIGHT -> 110;
            case Board.BBISHOP -> 98;
            case Board.BQUEEN -> 113;
            case Board.BKING -> 107;
            default -> throw new IllegalStateException("Unexpected value: " + piece);
        };
    }
}

