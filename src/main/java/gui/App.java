package gui;

import data.model.DataModel;
import gui.board.BoardMouseListener;
import gui.board.BoardPanel;
import gui.option.OptionPanel;

import javax.swing.*;
import java.awt.*;


public class App {
    private final JFrame frame;
    private final BoardPanel boardPanel;
    private final OptionPanel optionPanel;
    private final Controller controller;
    private final JMenuBar menuBar;

    public App(DataModel dataModel) {
        frame = new JFrame();
        menuBar = new JMenuBar();
        createFileMenu(dataModel);
        boardPanel = new BoardPanel(dataModel.getActualNode());
        controller = new Controller(dataModel, boardPanel);
        optionPanel = new OptionPanel(controller);
        controller.setOptionPanel(optionPanel);
        setUpBoardPanel();
        setUpFrame();
    }

    private void setUpFrame() {
        frame.setJMenuBar(menuBar);
        frame.setLayout(new GridLayout());
        frame.add(boardPanel);
        frame.add(optionPanel);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }

    private void createFileMenu(DataModel dataModel) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(e -> dataModel.saveDataToFile(getFilename()));
        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.addActionListener(e -> dataModel.loadDataFromFile(getFilename()));
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
    }

    private void setUpBoardPanel() {
        boardPanel.addMouseListener(new BoardMouseListener(controller));
        boardPanel.setFocusable(true);
        boardPanel.addKeyListener(new KeyboardListener(controller));
        boardPanel.setSize(480, 480);
    }

    private String getFilename() {
        return JOptionPane.showInputDialog(frame, "What is name of file?", null);
    }
}
