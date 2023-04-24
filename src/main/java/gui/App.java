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
        createFileMenu();
        createFeatureMenu();
        createEditionMenu();
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

    private void createFileMenu() {
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveJSONMenuItem = new JMenuItem("Save to json");
        saveJSONMenuItem.addActionListener(e -> controller.saveDataToJSON(getFilename()));
        fileMenu.add(saveJSONMenuItem);

        JMenuItem loadJSONMenuItem = new JMenuItem("Load from json");
        loadJSONMenuItem.addActionListener(e -> controller.loadDataFromJSON(getFilename()));
        fileMenu.add(loadJSONMenuItem);

        JMenuItem loadFENMenuItem = new JMenuItem("Load from FEN");
        loadFENMenuItem.addActionListener(e -> controller.loadChessBoardFromFEN(getFEN()));
        fileMenu.add(loadFENMenuItem);

        JMenuItem saveFENMenuItem = new JMenuItem("Save to FEN");
        saveFENMenuItem.addActionListener(e -> controller.saveChessBoardToFEN());
        fileMenu.add(saveFENMenuItem);

        JMenuItem loadPGNMenuItem = new JMenuItem("Load from PGN");
        loadPGNMenuItem.addActionListener(e -> controller.loadDataFromPGN(getFilename()));
        fileMenu.add(loadPGNMenuItem);

        menuBar.add(fileMenu);
    }

    private void createFeatureMenu() {
        JMenu featureMenu = new JMenu("Features");

        JMenuItem coverageMenuItem = new JMenuItem("Coverage");
        coverageMenuItem.addActionListener(e -> boardPanel.swapDoPaintCoverage());
        featureMenu.add(coverageMenuItem);

        JMenuItem legalMovesMenuItem = new JMenuItem("Legal moves");
        legalMovesMenuItem.addActionListener(e -> boardPanel.swapDoPaintLegalMoves());
        featureMenu.add(legalMovesMenuItem);

        JMenuItem weakPointsMenuItem = new JMenuItem("Weak points");
        weakPointsMenuItem.addActionListener(e -> boardPanel.swapDoPaintWeakPoints());
        featureMenu.add(weakPointsMenuItem);

        menuBar.add(featureMenu);
    }

    private void createEditionMenu() {
        JMenu editionMenu = new JMenu("Edit");

        JMenuItem addMovesItem = new JMenuItem("Add moves");
        addMovesItem.addActionListener(e -> controller.makeMoves(getMoves()));
        editionMenu.add(addMovesItem);

        JMenuItem insertPGNItem = new JMenuItem("Insert pgn");
        insertPGNItem.addActionListener(e -> controller.insertPGN(getFilename()));
        editionMenu.add(insertPGNItem);

        menuBar.add(editionMenu);
    }

    private void setUpBoardPanel() {
        boardPanel.addMouseListener(new BoardMouseListener(controller));
        boardPanel.setFocusable(true);
        boardPanel.setSize(480, 480);
    }

    private String getFilename() {
        return JOptionPane.showInputDialog(frame, "What is name of file?");
    }

    private String getFEN() {
        return JOptionPane.showInputDialog(frame, "Write FEN you want to import");
    }

    private String getMoves() {
        return JOptionPane.showInputDialog(frame, "Write moves you want to add");
    }
}
