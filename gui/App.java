package gui;

import javax.swing.*;
import java.awt.*;

public class App {
    JFrame frame;
    BoardPanel boardPanel;
    OptionPanel optionPanel;

    public App() {
        frame=new JFrame();
        boardPanel=new BoardPanel();
        optionPanel=new OptionPanel();
        frame.add(boardPanel, BorderLayout.WEST);
        frame.add(optionPanel, BorderLayout.EAST);
        frame.setSize(640,400);
        frame.setVisible(true);
    }
}
