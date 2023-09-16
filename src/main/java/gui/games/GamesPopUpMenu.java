package gui.games;

import data.model.metadata.GameData;
import gui.DialogManager;
import gui.controllers.MenuController;

import javax.swing.*;

public class GamesPopUpMenu extends JPopupMenu {
    public GamesPopUpMenu(MenuController menuController, GameData metaData, DialogManager dialogManager) {
        JMenuItem goToLeaf = new JMenuItem("Go to leaf");
        goToLeaf.addActionListener(e -> menuController.selectGame(metaData));
        add(goToLeaf);

        JMenuItem goToEnd = new JMenuItem("Go to end");
        goToEnd.addActionListener(e -> menuController.selectGameEnd(metaData));
        add(goToEnd);

        JMenuItem export = new JMenuItem("Export game");
        export.addActionListener(e -> menuController.export(dialogManager.getFilename(), metaData));
        add(export);
    }
}
