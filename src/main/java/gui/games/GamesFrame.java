package gui.games;

import data.model.DataModel;

import gui.DialogManager;
import gui.controllers.MenuController;

import javax.swing.*;

public class GamesFrame extends JFrame {
    private final GameTableModel tableModel;
    private final DataModel dataModel;
    private final DialogManager dialogManager;

    public GamesFrame(DataModel dataModel, MenuController controller) {
        dialogManager = new DialogManager(this);
        tableModel = new GameTableModel(dataModel.getGames().getGameData());
        JTable jTable = new JTable(tableModel);
        jTable.addMouseListener(new GamesTableMouseListener(jTable, controller, dialogManager));
        this.dataModel = dataModel;
        JScrollPane jScrollPane = new JScrollPane(jTable);
        this.add(jScrollPane);
        this.setSize(640, 480);
    }

    public void refresh() {
        tableModel.swapData(dataModel.getGames().getGameData());
    }
}
