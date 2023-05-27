package gui.games;

import data.model.DataModel;
import data.model.metadata.MetaData;
import gui.Controller;

import javax.swing.*;

public class GamesFrame extends JFrame {
    private final DefaultListModel<MetaData> listModel;
    private final DataModel dataModel;

    public GamesFrame(DataModel dataModel, Controller controller) {
        listModel = new DefaultListModel<>();
        JList<MetaData> list = new JList<>(listModel);
        list.addMouseListener(new GamesListMouseListener(list, controller));
        this.dataModel = dataModel;
        this.add(list);
        this.setSize(640, 480);
    }

    public void refresh() {
        listModel.removeAllElements();
        listModel.addAll(dataModel.getGames().getMetadata());
    }
}
