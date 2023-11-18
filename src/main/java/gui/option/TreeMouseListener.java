package gui.option;

import data.model.diagrams.Diagram;
import gui.controllers.Controller;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TreeMouseListener implements MouseListener {
    Controller controller;
    JTree tree;

    public TreeMouseListener(JTree tree,Controller controller) {
        this.tree = tree;
        this.controller=controller;
        controller.setTreeMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selRow = tree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
        if(selRow != -1 && e.getClickCount() == 2) {
            controller.setActualNode((Diagram) selPath.getLastPathComponent());
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

    public void treeNodeInserted(TreePath treePath){
        tree.setSelectionPath(treePath);
        tree.repaint();
    }
}
