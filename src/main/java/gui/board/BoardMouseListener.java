package gui.board;

import chess.board.lowlevel.Board;
import chess.Position;
import chess.moves.raw.RawMove;
import gui.controllers.BoardController;
import log.Log;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class BoardMouseListener implements MouseInputListener {
    private final BoardController controller;
    private int scale = 60;
    private int x = -1;
    private int y = -1;

    public BoardMouseListener(BoardController controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        controller.executeClickAction(Position.of(findField(e.getX()), reverse(findField(e.getY()))), new SpecialKeysMap(e));
        x = -1;
        y = -1;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        scale = controller.getScale();
        if (x != -1 && y != -1 && x != e.getX() && y != e.getY()) {
            Log.log().info("Drag = ( " + findField(x) + ", " + reverse(findField(y)) + ")->( " + findField(e.getX()) + ", " + reverse(findField(e.getY())));
            controller.executeDragAction(RawMove.of(
                            Position.of(findField(x), reverse(findField(y))),
                            Position.of(findField(e.getX()), reverse(findField(e.getY())))),
                    new SpecialKeysMap(e)
            );
            x = -1;
            y = -1;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public int findField(int t) {
        return ((t - (t % scale)) / scale) + 1;
    }

    public int reverse(int t) {
        return Board.SIZE - t + 1;
    }
}
