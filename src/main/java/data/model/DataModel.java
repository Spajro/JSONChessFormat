package data.model;

import chess.moves.RawMove;
import log.Log;

import java.util.*;

public class DataModel {
    private Diagram actualNode;
    private Map<MetaData, Diagram> matches = new HashMap<>();
    private final TreeDataModel treeDataModel = new TreeDataModel();
    private PromotionTypeProvider promotionTypeProvider;

    public DataModel() {
        actualNode = new Diagram();
        treeDataModel.setActualNode(actualNode);
    }

    public void makeMove(RawMove m) {
        Diagram tempNode = actualNode;
        actualNode = actualNode.makeMove(m, promotionTypeProvider);
        if (tempNode != actualNode) {
            treeDataModel.setActualNode(actualNode);
            treeDataModel.notifyListenersOnInsert(actualNode);
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

    public void insert(Diagram tree, MetaData metaData) {
        Log.log().info("DataModel insertion");
        Diagram actualRoot = actualNode.getRoot();
        Diagram insertedRoot = tree.getRoot();
        matches.put(metaData, getLast(insertedRoot).orElseThrow());
        actualRoot.insert(insertedRoot, metaData);
    }

    private Optional<Diagram> getLast(Diagram diagram) {
        return switch (diagram.getNextDiagrams().size()) {
            case 0 -> Optional.of(diagram);
            case 1 -> getLast(diagram.getNextDiagrams().getFirst());
            default -> Optional.empty();
        };
    }

    public void setActualNode(Diagram actualNode) {
        this.actualNode = actualNode;
        treeDataModel.setActualNode(actualNode);
    }

    public void setNewTree(Diagram tree) {
        this.actualNode = tree;
        treeDataModel.setActualNode(tree);
        matches = gatherMetadata();
    }

    public Diagram getActualNode() {
        return actualNode;
    }

    public void setPromotionTypeProvider(PromotionTypeProvider promotionTypeProvider) {
        this.promotionTypeProvider = promotionTypeProvider;
    }

    public TreeDataModel asTree() {
        return treeDataModel;
    }

    private Map<MetaData, Diagram> gatherMetadata() {
        Map<MetaData, Diagram> result = new HashMap<>();
        Diagram root = actualNode.getRoot();
        Stack<Diagram> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Diagram node = stack.pop();
            if (!node.getMetaData().isEmpty()) {
                node.getMetaData().forEach(metaData -> result.put(metaData, node));
            } else {
                stack.addAll(node.getNextDiagrams());
            }
        }
        return result;
    }
}
