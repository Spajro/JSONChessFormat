package gui;

import dts.Board;

import javax.swing.*;
import java.awt.*;

public class App {
    private JFrame frame;
    private BoardPanel boardPanel;
    private OptionPanel optionPanel;

    public App() {
        frame=new JFrame();
        boardPanel=new BoardPanel();
        optionPanel=new OptionPanel();
        boardPanel.addMouseListener(new MouseListener());
        frame.add(boardPanel, BorderLayout.WEST);
        frame.add(optionPanel, BorderLayout.EAST);
        frame.setSize(640,400);
        frame.setVisible(true);
    }

    public App(Board board) {
        frame=new JFrame();
        boardPanel=new BoardPanel(board);
        optionPanel=new OptionPanel();
        boardPanel.addMouseListener(new MouseListener());
        frame.add(boardPanel, BorderLayout.WEST);
        frame.add(optionPanel, BorderLayout.EAST);
        frame.setSize(640,400);
        frame.setVisible(true);
    }
    public void setBoard(Board board){
        boardPanel.setBoard(board);
    }
}
