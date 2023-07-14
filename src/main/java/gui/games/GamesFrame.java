package gui.games;

import data.model.DataModel;
import gui.Controller;

import javax.swing.*;

public class GamesFrame extends JFrame {
    private final GameTableModel tableModel;
    private final DataModel dataModel;

    public GamesFrame(DataModel dataModel, Controller controller) {
        tableModel = new GameTableModel(dataModel.getGames().getGameData());
        JTable jTable = new JTable(tableModel);
        jTable.addMouseListener(new GamesTableMouseListener(jTable, controller));
        this.dataModel = dataModel;
        JScrollPane jScrollPane = new JScrollPane(jTable);
        this.add(jScrollPane);
        this.setSize(640, 480);
    }

    public void refresh() {
        tableModel.swapData(dataModel.getGames().getGameData());
    }
}
