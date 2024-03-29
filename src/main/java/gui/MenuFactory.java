package gui;

import gui.board.BoardPanel;
import gui.controllers.MenuController;

import javax.swing.*;

public class MenuFactory {
    public JMenuBar createMenu(MenuController controller, BoardPanel boardPanel, DialogManager dialogManager) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu(controller, dialogManager));
        menuBar.add(createFeatureMenu(boardPanel, controller, dialogManager));
        return menuBar;
    }

    private JMenu createFileMenu(MenuController controller, DialogManager dialogManager) {
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveJSONMenuItem = new JMenuItem("Save to json");
        saveJSONMenuItem.addActionListener(e -> controller.saveDataToJSON(dialogManager.getFilename()));
        fileMenu.add(saveJSONMenuItem);

        JMenuItem saveFENMenuItem = new JMenuItem("Save to FEN");
        saveFENMenuItem.addActionListener(e -> controller.saveChessBoardToFEN());
        fileMenu.add(saveFENMenuItem);

        JMenuItem loadJSONMenuItem = new JMenuItem("Load from json");
        loadJSONMenuItem.addActionListener(e -> controller.loadDataFromJSON(dialogManager.getFilename()));
        fileMenu.add(loadJSONMenuItem);

        JMenuItem loadFENMenuItem = new JMenuItem("Load from FEN");
        loadFENMenuItem.addActionListener(e -> controller.loadChessBoardFromFEN(dialogManager.getFEN()));
        fileMenu.add(loadFENMenuItem);

        JMenuItem loadPGNMenuItem = new JMenuItem("Load from PGN");
        loadPGNMenuItem.addActionListener(e -> controller.loadDataFromPGN(dialogManager.getFilename()));
        fileMenu.add(loadPGNMenuItem);

        return fileMenu;
    }

    private JMenu createFeatureMenu(BoardPanel boardPanel, MenuController controller, DialogManager dialogManager) {
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

        JMenuItem showGamesItem = new JMenuItem("Show games");
        showGamesItem.addActionListener(e -> controller.showGames());
        featureMenu.add(showGamesItem);

        JMenuItem insertPGNItem = new JMenuItem("Insert pgn");
        insertPGNItem.addActionListener(e -> controller.insertPGN(dialogManager.getFilename()));
        featureMenu.add(insertPGNItem);

        return featureMenu;
    }
}
