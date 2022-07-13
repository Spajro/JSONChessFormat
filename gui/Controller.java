package gui;


import dts.Board;
import dts.DataModel;
import dts.Diagram;
import dts.Move;

import javax.swing.*;

public class Controller {
    private DataModel dataModel;
    private BoardMouseListener boardMouseListener;
    private TreeMouseListener treeMouseListener;

    public Controller(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    void makeMove(Move move){
        dataModel.makeMove(move);
        treeMouseListener.treeNodeChanged();

    }

    public Board getActualBoard() {
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
        boardMouseListener.boardChanged(lastPathComponent.getBoard());
    }

    public JTree createTreeWithDataModel(){
        return new JTree(dataModel);
    }
}
