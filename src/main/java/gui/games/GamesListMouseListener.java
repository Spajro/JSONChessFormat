package gui.games;

import data.model.metadata.MetaData;
import gui.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamesListMouseListener implements MouseListener {
    private final JList<MetaData> list;
    private final Controller controller;

    public GamesListMouseListener(JList<MetaData> list, Controller controller) {
        this.list = list;
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex());
        if (r != null && r.contains(e.getPoint())) {
            int index = list.locationToIndex(e.getPoint());

            if (e.getClickCount() == 2) {
                controller.selectGame(list.getModel().getElementAt(index));

            } else if (e.getClickCount() == 3) {
                controller.selectGameEnd(list.getModel().getElementAt(index));
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
