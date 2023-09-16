package gui.games;

import gui.DialogManager;
import gui.controllers.MenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamesTableMouseListener implements MouseListener {
    private final JTable table;
    private final GameTableModel gameTableModel;
    private final MenuController controller;
    private final DialogManager dialogManager;

    public GamesTableMouseListener(JTable table, MenuController controller, DialogManager dialogManager) {
        this.table = table;
        this.controller = controller;
        this.gameTableModel = (GameTableModel) table.getModel();
        this.dialogManager = dialogManager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        popup(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        popup(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void popup(MouseEvent e) {
        if (!e.isPopupTrigger()) {
            return;
        }
        Rectangle r = table.getBounds();
        if (r.contains(e.getPoint())) {
            int index = table.rowAtPoint(e.getPoint());
            new GamesPopUpMenu(controller, gameTableModel.getGameDataForRow(index), dialogManager)
                    .show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
