package gui;

import data.model.DataModel;
import gui.board.BoardMouseListener;
import gui.board.BoardPanel;
import gui.controllers.Controller;
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
        boardPanel = new BoardPanel(dataModel.getActualNode());
        controller = new Controller(dataModel, boardPanel);
        optionPanel = new OptionPanel(controller);
        DialogManager dialogManager = new DialogManager(frame);
        menuBar = new MenuFactory().createMenu(controller.getMenuController(), boardPanel, dialogManager);
        controller.setOptionPanel(optionPanel);
        setUpBoardPanel();
        setUpFrame();
    }

    private void setUpFrame() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.setLayout(new GridLayout());
        frame.add(boardPanel);
        frame.add(optionPanel);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }

    private void setUpBoardPanel() {
        boardPanel.addMouseListener(new BoardMouseListener(controller.getBoardController()));
        boardPanel.setFocusable(true);
        boardPanel.setSize(480, 480);
    }
}
