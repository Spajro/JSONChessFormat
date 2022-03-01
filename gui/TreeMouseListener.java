package gui;

import dts.DataModel;
import dts.Diagram;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TreeMouseListener implements MouseListener {
    JTree tree;
    DataModel dataModel;

    public TreeMouseListener(JTree tree, DataModel dataModel) {
        this.tree = tree;
        this.dataModel = dataModel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selRow = tree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
        if(selRow != -1 && e.getClickCount() == 2) {
            dataModel.setActualNode((Diagram) selPath.getLastPathComponent());
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
