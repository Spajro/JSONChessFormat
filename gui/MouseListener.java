package gui;

import dts.Board;
import dts.DataModel;
import dts.Move;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class MouseListener implements MouseInputListener {
    BoardPanel boardPanel;
    DataModel model;
    private int x=-1;
    private int y=-1;

    public MouseListener(BoardPanel boardPanel, DataModel model) {
        this.boardPanel = boardPanel;
        this.model = model;
    }

    public int findField(int t){
       return ((t-(t%boardPanel.getScale()))/boardPanel.getScale())+1;
    }
    public int reverse(int t){
        return Board.SIZE-t+1;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

        x=-1;
        y=-1;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x=e.getX();
        y=e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(x!=-1 && y!=-1 && x!=e.getX() && y!=e.getY()){
            System.err.print("DRAG\n");
            System.err.print("( "+findField(x)+", "+reverse(findField(y))+")->( "+findField(e.getX())+", "+reverse(findField(e.getY()))+")\n");
            model.makeMove(new Move(findField(x),reverse(findField(y)),findField(e.getX()),reverse(findField(e.getY()))));
            boardPanel.setBoard(model.getActualBoard()); //
            boardPanel.repaint();
            x=-1;
            y=-1;
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
}
