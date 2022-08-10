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

    public Controller(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    void makeMove(RawMove move){
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
