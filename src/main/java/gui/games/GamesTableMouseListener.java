package gui.games;

import gui.controllers.MenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamesTableMouseListener implements MouseListener {
    private final JTable table;
    private final GameTableModel gameTableModel;
    private final MenuController controller;

    public GamesTableMouseListener(JTable table, MenuController controller) {
        this.table = table;
        this.controller = controller;
        this.gameTableModel = (GameTableModel) table.getModel();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Rectangle r = table.getBounds();
        if (r != null && r.contains(e.getPoint())) {
            int index = table.rowAtPoint(e.getPoint());

            if (e.getClickCount() == 2) {
                controller.selectGame(gameTableModel.getMetaDataForRow(index));

            } else if (e.getClickCount() == 3) {
                controller.selectGameEnd(gameTableModel.getMetaDataForRow(index));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
