package gui;

import dts.DataModel;
import dts.Move;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class MouseListener implements MouseInputListener {
    BoardPanel boardPanel;
    DataModel model;
    private int x=-1;
    private int y=-1;


    public int findField(int t){
       return ((t-(t%boardPanel.getScale()))/20)+1;
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
            model.makeMove(new Move(findField(x),findField(y),findField(e.getX()),findField(e.getY())));
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
