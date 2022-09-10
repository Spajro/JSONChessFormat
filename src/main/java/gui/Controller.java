package gui;

import data.model.DataModel;
import data.model.Diagram;
import chess.board.ChessBoard;
import chess.moves.RawMove;

import javax.swing.*;

public class Controller {
    private final DataModel dataModel;
    private BoardMouseListener boardMouseListener;
    private TreeMouseListener treeMouseListener;
    private KeyboardListener keyboardListener;
    private boolean isEditingAnnotation = false;

    public Controller(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    void makeMove(RawMove move) {
        dataModel.makeMove(move);
        treeMouseListener.treeNodeInserted(dataModel.getTreePathTo(dataModel.getActualNode()));

    }

    public ChessBoard getActualBoard() {
        return dataModel.getActualBoard();
    }

    public void setBoardMouseListener(BoardMouseListener boardMouseListener) {
        this.boardMouseListener = boardMouseListener;
    }

    public void setTreeMouseListener(TreeMouseListener treeMouseListener) {
        this.treeMouseListener = treeMouseListener;
    }

    public void setActualNode(Diagram lastPathComponent) {
        dataModel.setActualNode(lastPathComponent);
        boardMouseListener.diagramChanged(lastPathComponent);
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

    public Diagram getActualDiagram() {
        return dataModel.getActualNode();
    }
}
