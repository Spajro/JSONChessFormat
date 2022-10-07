package gui;

import chess.Position;
import data.annotations.ArrowAnnotation;
import data.annotations.FieldAnnotation;
import data.annotations.GraphicAnnotation;
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

    public void executeDragEvent(RawMove move) {
        if (isEditingAnnotation) {
            if (arrowAnnotationExists(move)) {
                dataModel.getActualNode()
                        .getAnnotations()
                        .getArrowAnnotations()
                        .removeIf(arrowAnnotation -> arrowAnnotation.equals(move));
            } else {
                dataModel.getActualNode()
                        .getAnnotations()
                        .addArrowAnnotation(new ArrowAnnotation(move, getDrawColor()));
            }
        } else {
            dataModel.makeMove(move);
            boardPanel.setDiagram(dataModel.getActualNode());
            treeMouseListener.treeNodeInserted(dataModel.getTreePathTo(dataModel.getActualNode()));

        }
        boardPanel.repaint();
    }

    private boolean arrowAnnotationExists(RawMove move) {
        return dataModel.getActualNode()
                .getAnnotations()
                .getArrowAnnotations()
                .stream()
                .anyMatch(arrowAnnotation -> arrowAnnotation.equals(move));
    }

    public void executeClickAction(Position position) {
        if (isEditingAnnotation) {
            if (fieldAnnotationExists(position)) {
                dataModel.getActualNode()
                        .getAnnotations()
                        .getFieldAnnotations()
                        .removeIf(fieldAnnotation -> fieldAnnotation.equals(position));
            } else {
                dataModel.getActualNode()
                        .getAnnotations()
                        .addFieldAnnotation(new FieldAnnotation(position, getDrawColor()));
            }
        }
    }

    private boolean fieldAnnotationExists(Position position) {
        return dataModel.getActualNode()
                .getAnnotations()
                .getFieldAnnotations()
                .stream()
                .anyMatch(fieldAnnotation -> fieldAnnotation.equals(position));
    }

    private GraphicAnnotation.DrawColor getDrawColor() {
        return GraphicAnnotation.DrawColor.BLUE;
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
