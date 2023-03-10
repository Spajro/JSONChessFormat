package gui;

import chess.Position;
import data.annotations.ArrowAnnotation;
import data.annotations.FieldAnnotation;
import data.annotations.GraphicAnnotation;
import data.model.DataModel;
import data.model.Diagram;
import chess.moves.RawMove;
import gui.board.BoardPanel;
import gui.option.OptionPanel;
import gui.option.TreeMouseListener;
import log.Log;

import javax.swing.*;

public class Controller {
    private final DataModel dataModel;
    private final BoardPanel boardPanel;
    private OptionPanel optionPanel;
    private TreeMouseListener treeMouseListener;
    private KeyboardListener keyboardListener;
    private boolean isEditingAnnotation = false;

    public Controller(DataModel dataModel, BoardPanel boardPanel) {
        this.dataModel = dataModel;
        this.boardPanel = boardPanel;
    }

    public void executeDragAction(RawMove move) {
        if (isEditingAnnotation) {
            if (arrowAnnotationExists(move)) {
                Log.log().info("Remove arrow annotation " + move);
                dataModel.getActualNode()
                        .getAnnotations()
                        .getArrowAnnotations()
                        .removeIf(arrowAnnotation -> arrowAnnotation.moveEquals(move));
            } else {
                Log.log().info("Add arrow annotation " + move);
                dataModel.getActualNode()
                        .getAnnotations()
                        .addArrowAnnotation(new ArrowAnnotation(move, getDrawColor()));
            }
        } else {
            dataModel.makeMove(move);
            boardPanel.setDiagram(dataModel.getActualNode());
            treeMouseListener.treeNodeInserted(dataModel.getTreePathTo(dataModel.getActualNode()));
            optionPanel.setText(dataModel.getActualNode().getAnnotations().getTextAnnotation());
        }
        boardPanel.repaint();
    }

    private boolean arrowAnnotationExists(RawMove move) {
        return dataModel.getActualNode()
                .getAnnotations()
                .getArrowAnnotations()
                .stream()
                .anyMatch(arrowAnnotation -> arrowAnnotation.moveEquals(move));
    }

    public void executeClickAction(Position position) {
        if (isEditingAnnotation) {
            if (fieldAnnotationExists(position)) {
                Log.log().info("Remove field annotation " + position);
                dataModel.getActualNode()
                        .getAnnotations()
                        .getFieldAnnotations()
                        .removeIf(fieldAnnotation -> fieldAnnotation.positionEquals(position));
            } else {
                Log.log().info("Add field annotation " + position);
                dataModel.getActualNode()
                        .getAnnotations()
                        .addFieldAnnotation(new FieldAnnotation(position, getDrawColor()));
            }
            boardPanel.repaint();
        }
    }

    private boolean fieldAnnotationExists(Position position) {
        return dataModel.getActualNode()
                .getAnnotations()
                .getFieldAnnotations()
                .stream()
                .anyMatch(fieldAnnotation -> fieldAnnotation.positionEquals(position));
    }

    private GraphicAnnotation.DrawColor getDrawColor() {
        return GraphicAnnotation.DrawColor.BLUE;
    }

    public void setTreeMouseListener(TreeMouseListener treeMouseListener) {
        this.treeMouseListener = treeMouseListener;
    }

    public void setActualNode(Diagram lastPathComponent) {
        Log.log().info(lastPathComponent.toString() + " is set as actual node");
        dataModel.setActualNode(lastPathComponent);
        boardPanel.setDiagram(lastPathComponent);
        optionPanel.setText(lastPathComponent.getAnnotations().getTextAnnotation());
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

    public void setOptionPanel(OptionPanel optionPanel) {
        this.optionPanel = optionPanel;
    }

    public void setTextAnnotation(String text) {
        dataModel.getActualNode().getAnnotations().setTextAnnotation(text);
    }

    public boolean isEditingAnnotation() {
        return isEditingAnnotation;
    }
}
