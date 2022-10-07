package gui;

import data.model.DataModel;
import data.model.Diagram;
import chess.moves.RawMove;

import javax.swing.*;

public class Controller {
    private final DataModel dataModel;
    private final BoardPanel boardPanel;
    private TreeMouseListener treeMouseListener;
    private KeyboardListener keyboardListener;
    private boolean isEditingAnnotation = false;

    public Controller(DataModel dataModel, BoardPanel boardPanel) {
        this.dataModel = dataModel;
        this.boardPanel = boardPanel;
    }

    void executeDragEvent(RawMove move) {
        dataModel.makeMove(move);
        boardPanel.setDiagram(dataModel.getActualNode());
        boardPanel.repaint();
        treeMouseListener.treeNodeInserted(dataModel.getTreePathTo(dataModel.getActualNode()));
    }

    public void setTreeMouseListener(TreeMouseListener treeMouseListener) {
        this.treeMouseListener = treeMouseListener;
    }

    public void setActualNode(Diagram lastPathComponent) {
        dataModel.setActualNode(lastPathComponent);
        boardPanel.setDiagram(lastPathComponent);
    }

    public JTree createTreeWithDataModel() {
        return new JTree(dataModel);
    }

    public void setKeyboardListener(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    public void setEditingAnnotation(boolean editingAnnotation) {
        isEditingAnnotation = editingAnnotation;
    }

    public int getScale() {
        return boardPanel.getScale();
    }
}
