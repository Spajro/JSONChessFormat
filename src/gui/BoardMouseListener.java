package src.gui;

import src.data.dts.board.Board;
import src.data.dts.Move;
import src.data.dts.Position;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class BoardMouseListener implements MouseInputListener {

    BoardPanel boardPanel;
    Controller controller;
    private int x=-1;
    private int y=-1;

    public BoardMouseListener(BoardPanel boardPanel, Controller controller) {
        this.boardPanel = boardPanel;
        this.controller = controller;
        controller.setBoardMouseListener(this);
    }

    public int findField(int t){
       return ((t-(t%boardPanel.getScale()))/boardPanel.getScale())+1;
    }
    public int reverse(int t){
        return Board.SIZE-t+1;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        System.err.print("Click:"+e.getX()+" "+e.getY()+"\n");
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
            controller.makeMove(new Move(
                    new Position(findField(x),reverse(findField(y))),
                    new Position(findField(e.getX()),reverse(findField(e.getY())))
            ));
            boardPanel.setBoard(controller.getActualBoard().getBoard()); //
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

    public void boardChanged(Board board) {
        boardPanel.setBoard(board);
    }
}
