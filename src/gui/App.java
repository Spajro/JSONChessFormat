package src.gui;

import src.data.dts.Board;
import src.data.dts.DataModel;

import javax.swing.*;
import java.awt.*;


public class App {
    private JFrame frame;
    private BoardPanel boardPanel;
    private OptionPanel optionPanel;
    private Controller controller;
    public App(DataModel dataModel) {
        controller=new Controller(dataModel);
        frame=new JFrame();
        boardPanel=new BoardPanel(dataModel.getActualBoard());
        optionPanel=new OptionPanel(controller);
        boardPanel.addMouseListener(new BoardMouseListener(boardPanel,controller));
        boardPanel.setSize(480,480);
        //optionPanel.setSize(160,480);
        frame.add(boardPanel,BorderLayout.CENTER);
        frame.add(optionPanel,BorderLayout.EAST);
        frame.setSize(640,480);
        frame.setVisible(true);
    }
    public void setBoard(Board board){
        boardPanel.setBoard(board);
    }
}
