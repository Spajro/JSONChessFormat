package src.gui;


import src.data.dts.board.Board;
import src.data.dts.DataModel;
import src.data.dts.Diagram;
import src.data.dts.Move;
import src.data.dts.board.ChessBoard;

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
        boardMouseListener.boardChanged(lastPathComponent.getBoard().getBoard());
    }

    public JTree createTreeWithDataModel(){
        return new JTree(dataModel);
    }
}
