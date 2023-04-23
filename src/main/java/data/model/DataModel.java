package data.model;

import chess.moves.RawMove;

import java.util.List;

public class DataModel {
    private Diagram actualNode;
    private final TreeDataModel treeDataMode = new TreeDataModel();
    private PromotionTypeProvider promotionTypeProvider;

    public DataModel() {
        actualNode = new Diagram();
        treeDataMode.setActualNode(actualNode);
    }

    public void makeMove(RawMove m) {
        Diagram tempNode = actualNode;
        actualNode = actualNode.makeMove(m, promotionTypeProvider);
        if (tempNode != actualNode) {
            treeDataMode.setActualNode(actualNode);
            treeDataMode.notifyListenersOnInsert(actualNode);
        }
    }

    public void makeMoves(List<RawMove> moves) {
        Diagram tempNode = actualNode;
        for (RawMove move : moves) {
            makeMove(move);
            if (actualNode == tempNode) {
                return;
            }
        }
    }

    public void setActualNode(Diagram actualNode) {
        this.actualNode = actualNode;
    }

    public Diagram getActualNode() {
        return actualNode;
    }

    public void setPromotionTypeProvider(PromotionTypeProvider promotionTypeProvider) {
        this.promotionTypeProvider = promotionTypeProvider;
    }

    public TreeDataModel asTree() {
        return treeDataMode;
    }
}
