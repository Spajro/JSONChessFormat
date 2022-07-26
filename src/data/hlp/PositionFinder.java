package src.data.hlp;

import src.data.dts.Board;
import src.data.dts.Position;
import src.data.dts.color.Color;

public class PositionFinder {
    public static Position[] basicSteps = {new Position(-1, 0), new Position(1, 0), new Position(0, 1), new Position(0, -1)};
    public static Position[] knightSteps = {
            new Position(2, 1),
            new Position(2, -1),
            new Position(1, 2),
            new Position(1, -2),
            new Position(-1, 2),
            new Position(-1, -2),
            new Position(-2, 1),
            new Position(-2, -1),
    };
    public static Position[] diagonalSteps = {new Position(-1, -1), new Position(1, 1), new Position(-1, 1), new Position(1, -1)};
    public static Position[] fullSteps = {
            new Position(1, 0),
            new Position(-1, 0),
            new Position(0, 1),
            new Position(0, -1),
            new Position(1, 1),
            new Position(1, -1),
            new Position(-1, 1),
            new Position(-1, -1),
    };

    public static Position chooseFig(char C, Color color, Board board, Position start, Position aux) {
        switch (C) {
            case ' ' -> {
                if (!aux.isEmpty()) {
                    System.err.print("aux for pawn");
                }
                return pawn(color, board, start);
            }
            case 'X' -> {
                return pawnCapture(color, board, start, aux);
            }
            case 'W' -> {
                return rook(color, board, start, aux);
            }
            case 'S' -> {
                return knight(color, board, start, aux);
            }
            case 'G' -> {
                return bishop(color, board, start, aux);
            }
            case 'H' -> {
                return queen(color, board, start, aux);
            }
            case 'K' -> {
                return king(color, board, start, aux);
            }
            default -> System.out.print("Choose_fig fault");
        }
        return new Position();
    }

    private static Position pawnCapture(Color color, Board board, Position start, Position aux) {
        //Manualnie
        Position result = new Position(start);
        if (color.isWhite()) {
            start = start.add(new Position(0, -1));
            if (aux.getX() == -1) {
                if (start.getX() == 1) {
                    result = result.add(new Position(1, 0));
                } else if (start.getX() == 8) {
                    result = result.add(new Position(-1, 0));
                } else {
                    if (board.checkPiece(Board.WPAWN, new Position(start.getX() + 1, start.getY()))) {
                        result = result.add(new Position(1, 0));
                    } else if (board.checkPiece(Board.WPAWN, new Position(start.getX() - 1, start.getY()))) {
                        result = result.add(new Position(-1, 0));
                    } else {
                        System.out.print("Pawn_capture fault");
                    }
                }
            } else {
                result = new Position(aux.getX(), start.getY());
            }
        } else {
            start = start.add(new Position(0, 1));
            if (aux.getX() == -1) {
                if (start.getX() == 1) {
                    result = result.add(new Position(1, 0));
                } else if (start.getX() == 8) {
                    result = result.add(new Position(-1, 0));
                } else {
                    if (board.checkPiece(Board.BPAWN, new Position(start.getX() + 1, start.getY()))) {
                        result = result.add(new Position(1, 0));
                    } else if (board.checkPiece(Board.BPAWN, new Position(start.getX() - 1, start.getY()))) {
                        result = result.add(new Position(-1, 0));
                    } else {
                        System.out.print("Pawn_capture fault");
                    }
                }
            } else {
                result = new Position(aux.getX(), start.getY());
            }
        }
        return result;
    }

    private static Position pawn(Color color, Board board, Position start) {
        if (color.isWhite()) {
            //biale
            return checkLine(start, new Position(0, -1), Board.WPAWN, board);
        } else {
            //czarne
            return checkLine(start, new Position(0, 1), Board.BPAWN, board);
        }
    }

    private static Position rook(Color color, Board board, Position start, Position aux) {
        Position result = new Position();
        if (aux.isEmpty()) {
            Position temp = new Position((start));
            int i = 0;
            while (result.isEmpty()) {
                if (color.isWhite()) checkLine(temp, basicSteps[i], Board.WROOK, board);
                else checkLine(temp, basicSteps[i], Board.BROOK, board);
                i++;
                if (i > 3) {
                    System.out.print("Rook while fail");
                    break;
                }
            }
        } else {
            if (aux.getX() != -1) {
                //TODO
            } else {
                //TODO
            }
        }
        return result;
    }

    private static Position knight(Color color, Board board, Position start, Position aux) {
        if (aux.isEmpty()) {
            for (Position p : knightSteps) {
                p.add(start);
                if (color.isWhite()) {
                    if (board.checkPiece(Board.WKNIGHT, p)) {
                        return p;
                    }
                } else {
                    if (board.checkPiece(Board.BKNIGHT, p)) {
                        return p;
                    }
                }
            }
        } else {
            //TODO
        }
        return new Position();
    }

    private static Position bishop(Color color, Board board, Position start, Position aux) {
        Position result = new Position();
        if (aux.isEmpty()) {
            Position temp = new Position((start));
            int i = 0;
            while (result.isEmpty()) {
                if (color.isWhite()) result = checkLine(temp, diagonalSteps[i], Board.WBISHOP, board);
                else result = checkLine(temp, diagonalSteps[i], Board.BBISHOP, board);
                i++;
                if (i > 3) {
                    System.out.print("Bishop while fail");
                    break;
                }
            }
        } else {
            if (aux.getX() != -1) {
                //TODO
            } else {
                //TODO
            }
        }
        return result;
    }

    private static Position queen(Color color, Board board, Position start, Position aux) {
        if (aux.isEmpty()) {
            for (Position p : fullSteps) {
                p.add(start);
                if (p.isOnBoard()) {
                    if (color.isWhite()) if (board.checkPiece(Board.WQUEEN, p)) return p;
                    else if (board.checkPiece(Board.BQUEEN, p)) return p;
                }
            }
        } else {
            //TODO
        }
        return new Position();
    }

    private static Position king(Color color, Board board, Position start, Position aux) {
        for (Position p : fullSteps) {
            p.add(start);
            if (p.isOnBoard()) {
                if (color.isWhite()) if (board.checkPiece(Board.WKING, p)) return p;
                else if (board.checkPiece(Board.BKING, p)) return p;
            }
        }
        return new Position();
    }

    public static Position checkLine(Position start, Position step, int figId, Board board) {
        Position temp = new Position(start);
        while (temp.isOnBoard()) {
            if (board.checkPiece(figId, temp)) {
                return temp;
            } else {
                if (!board.checkPiece(0, temp)) {
                    //jesli na linii jest inna figura
                    break;
                }
                temp.add(step);
            }
        }
        return new Position(-1, -1);
    }
}
