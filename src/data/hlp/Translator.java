package src.data.hlp;

import src.data.dts.board.Board;
import src.data.dts.Move;
import src.data.dts.Position;
import src.data.dts.board.BoardWrapper;
import src.data.dts.board.ChessBoard;
import src.data.dts.color.Color;

public class Translator {

    public static Move algebraicToMove(ChessBoard board, String moveName, Color color) {
        Move result = new Move();
        result.setName(moveName);
        boolean isCastle = false;
        boolean isFault = false;
        int castleType = -1;
        char textX = 0;
        char textY = 0;
        char pieceAlgebraicSymbol = 0;
        //TODO auxilliary information from notation
        char thx = 0;
        int auxiliaryX = -1;
        int auxiliaryY = -1;
        switch (moveName.length()) {
            case 2: //ruch pionka do przodu
                textX = moveName.charAt(0);
                textY = moveName.charAt(1);
                pieceAlgebraicSymbol = ' ';
                break;
            case 3: //ruch figury lub bicie pionem lub roszada krotka
                if (moveName.equals("O-O")) {
                    //roszada krotka
                    isCastle = true;
                    if (board.getColor().isWhite()) {
                        castleType = 1;
                    } else {
                        castleType = 3;
                    }
                } else if (moveName.charAt(0) == 'x') {
                    //bicie pionkiem
                    textX = moveName.charAt(1);
                    textY = moveName.charAt(2);
                    pieceAlgebraicSymbol = 'X';
                } else {
                    //ruch figury
                    pieceAlgebraicSymbol = moveName.charAt(0);
                    textX = moveName.charAt(1);
                    textY = moveName.charAt(2);
                }
                break;
            case 4: //ruch jednej z mozliwych figur lub bicie figurą lub jednym z mozliwych pionkow
                if (moveName.charAt(1) == 'x') {
                    //bicie figura
                    pieceAlgebraicSymbol = moveName.charAt(0);
                    textX = moveName.charAt(2);
                    textY = moveName.charAt(3);
                } else if (moveName.charAt(0) == 'x') {
                    //bicie jednym z mozliwych pionkow
                    thx = moveName.charAt(1);
                    textX = moveName.charAt(2);
                    textY = moveName.charAt(3);
                    pieceAlgebraicSymbol = 'X';
                } else {
                    //ruch jednej z mozliwych figur
                    pieceAlgebraicSymbol = moveName.charAt(0);
                    thx = moveName.charAt(1);
                    textX = moveName.charAt(2);
                    textY = moveName.charAt(3);
                }
                break;
            case 5: //bicie jedną z mozliwych figur lub roszada dluga
                if (moveName.equals("O-O-O")) {
                    //roszada długa
                    isCastle = true;
                    if (board.getColor().isWhite()) {
                        castleType = 2;
                    } else {
                        castleType = 4;
                    }
                } else {
                    //bicie jedną z mozliwych figur
                    pieceAlgebraicSymbol = moveName.charAt(0);
                    thx = moveName.charAt(2);
                    textX = moveName.charAt(3);
                    textY = moveName.charAt(4);
                }
                break;
            default:
                isFault = true;
                System.out.print("Unable to translate");
                break;
        }
        if (isFault) {
            return null;
        } else if (isCastle) {
            result.setCastle(castleType);
        } else {
            Position endPosition = new Position(columnToNumber(textX), Character.getNumericValue(textY) - 1);
            Position temp = BoardWrapper.createPieceFromId(board, algebraicPieceToBoardId(pieceAlgebraicSymbol, color), endPosition).getPosition();
            result = new Move(temp, endPosition);
        }
        return result;
    }

    public static String preMoveToAlgebraic(ChessBoard board, Move move) {
        if (move.getCastle() == Move.NO_CASTLE) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append(numberToFigure(board.read(move.getOldPosition())));
            if (board.read(move.getNewPosition()) != Board.EMPTY) {
                stringBuilder.append("x");
            }
            stringBuilder
                    .append(numberToColumn(move.getNewPosition().getX()))
                    .append(move.getNewPosition().getY());
            return stringBuilder.toString();
        } else {
            switch (move.getCastle()) {
                case Move.WHITE_SHORT_CASTLE, Move.BLACK_SHORT_CASTLE -> {
                    return "O-O";
                }
                case Move.WHITE_LONG_CASTLE, Move.BLACK_LONG_CASTLE -> {
                    return "O-O-O";
                }
                default -> System.out.print("preMoveToAlgebraic fault on castle\n");

            }
        }
        return "ERROR";
    }

    public static int columnToNumber(char column) {
        switch (column) {
            case 'a' -> {
                return 1;
            }
            case 'b' -> {
                return 2;
            }
            case 'c' -> {
                return 3;
            }
            case 'd' -> {
                return 4;
            }
            case 'e' -> {
                return 5;
            }
            case 'f' -> {
                return 6;
            }
            case 'g' -> {
                return 7;
            }
            case 'h' -> {
                return 8;
            }
            default -> System.out.print("columnToNumber fault\n");
        }
        return -1;
    }

    public static String numberToFigure(int figure) {
        switch (figure) {
            case Board.WPAWN -> {
                return "BP";
            }
            case Board.WROOK -> {
                return "BW";
            }
            case Board.WKNIGHT -> {
                return "BS";
            }
            case Board.WBISHOP -> {
                return "BG";
            }
            case Board.WQUEEN -> {
                return "BH";
            }
            case Board.WKING -> {
                return "BK";
            }
            case Board.BPAWN -> {
                return "CP";
            }
            case Board.BROOK -> {
                return "CW";
            }
            case Board.BKNIGHT -> {
                return "CS";
            }
            case Board.BBISHOP -> {
                return "CG";
            }
            case Board.BQUEEN -> {
                return "CH";
            }
            case Board.BKING -> {
                return "CK";
            }
            default -> {
                System.out.print("numberToFigure fault\n");
                return null;
            }
        }
    }

    public static char numberToColumn(int column) {
        switch (column) {
            case 1 -> {
                return 'a';
            }
            case 2 -> {
                return 'b';
            }
            case 3 -> {
                return 'c';
            }
            case 4 -> {
                return 'd';
            }
            case 5 -> {
                return 'e';
            }
            case 6 -> {
                return 'f';
            }
            case 7 -> {
                return 'g';
            }
            case 8 -> {
                return 'h';
            }
            default -> System.out.print("numberToColumn fault\n");
        }
        return '-';
    }

    public static int algebraicPieceToBoardId(char c, Color color) {
        if (color.isWhite()) {
            switch (c) {
                case ' ' -> {
                    return Board.WPAWN;
                }
                case 'R' -> {
                    return Board.WROOK;
                }
                case 'N' -> {
                    return Board.WKNIGHT;
                }
                case 'B' -> {
                    return Board.WBISHOP;
                }
                case 'Q' -> {
                    return Board.WQUEEN;
                }
                case 'K' -> {
                    return Board.WKING;
                }
            }
        }
        if (color.isBlack()) {
            switch (c) {
                case ' ' -> {
                    return Board.BPAWN;
                }
                case 'R' -> {
                    return Board.BROOK;
                }
                case 'N' -> {
                    return Board.BKNIGHT;
                }
                case 'B' -> {
                    return Board.BBISHOP;
                }
                case 'Q' -> {
                    return Board.BQUEEN;
                }
                case 'K' -> {
                    return Board.BKING;
                }
            }
        }
        return -1;
    }
}


