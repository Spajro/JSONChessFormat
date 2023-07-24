package gui.controllers;

import data.model.DataModel;
import data.model.Diagram;
import data.model.PromotionTypeProvider;
import gui.DialogPromotionTypeProvider;
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
    private final BoardController boardController;
    private final MenuController menuController;

    public Controller(DataModel dataModel, BoardPanel boardPanel) {
        this.dataModel = dataModel;
        this.boardPanel = boardPanel;
        dataModel.setPromotionTypeProvider(getPromotionTypeProvider());
        boardController = new BoardController(dataModel, boardPanel, this);
        menuController = new MenuController(dataModel, boardPanel);
    }

    public void setTreeMouseListener(TreeMouseListener treeMouseListener) {
        this.treeMouseListener = treeMouseListener;
    }

    public void setActualNode(Diagram lastPathComponent) {
        Log.log().info(lastPathComponent.toString() + " is set as actual node, " + lastPathComponent.getNextDiagrams().size() + " nextDiagrams");
        dataModel.setActualNode(lastPathComponent);
        boardPanel.setDiagram(lastPathComponent);
        optionPanel.setText(lastPathComponent.getAnnotations().getTextAnnotation());
    }

    public JTree createTreeWithDataModel() {
        return new JTree(dataModel.asTree());
    }

    private PromotionTypeProvider getPromotionTypeProvider() {
        return new DialogPromotionTypeProvider(optionPanel);
    }

    public void setOptionPanel(OptionPanel optionPanel) {
        this.optionPanel = optionPanel;
        menuController.setOptionPanel(optionPanel);
    }

    public void setTextAnnotation(String text) {
        dataModel.getActualNode().getAnnotations().setTextAnnotation(text);
    }

    public void updateOnInsert() {
        treeMouseListener.treeNodeInserted(dataModel.asTree().getTreePathTo(dataModel.getActualNode()));
        optionPanel.setText(dataModel.getActualNode().getAnnotations().getTextAnnotation());
    }

    public BoardController getBoardController() {
        return boardController;
    }

    public MenuController getMenuController() {
        return menuController;
    }
}
