package gui.board;

import chess.board.Board;
import chess.Position;
import chess.moves.RawMove;
import gui.Controller;
import log.Log;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class BoardMouseListener implements MouseInputListener {
    private final Controller controller;
    private int scale = 60;
    private int x = -1;
    private int y = -1;

    public BoardMouseListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        controller.executeClickAction(new Position(findField(e.getX()),reverse(findField(e.getY()))));
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
        scale=controller.getScale();
        if (x != -1 && y != -1 && x != e.getX() && y != e.getY()) {
            Log.log().info("Drag = ( " + findField(x) + ", " + reverse(findField(y)) + ")->( " + findField(e.getX()) + ", " + reverse(findField(e.getY())));
            controller.executeDragAction(new RawMove(
                    new Position(findField(x), reverse(findField(y))),
                    new Position(findField(e.getX()), reverse(findField(e.getY())))
            ));
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
