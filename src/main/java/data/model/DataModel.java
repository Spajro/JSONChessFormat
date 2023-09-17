package data.model;

import chess.moves.raw.RawMove;
import data.model.games.GamesRepository;
import data.model.metadata.MetaData;
import log.Log;

import java.util.*;

public class DataModel {
    private Diagram actualNode;
    private final GamesRepository games = new GamesRepository();
    private final TreeDataModel treeDataModel = new TreeDataModel(this);
    private final DiagramController diagramController = new DiagramController();
    private PromotionTypeProvider promotionTypeProvider;

    public DataModel() {
        actualNode = new Diagram();
    }

    public void makeMove(RawMove move) {
        Log.log().info("Make move: " + move);
        Diagram tempNode = actualNode;
        actualNode = diagramController.makeMove(actualNode, move, promotionTypeProvider);
        if (tempNode != actualNode) {
            treeDataModel.notifyListenersOnInsert(actualNode);
        }
    }

    public void insert(ArrayDeque<RawMove> moves, MetaData metaData) {
        Log.log().info("Insert: " + metaData);
        Diagram actualRoot = actualNode.getRoot();
        games.update(diagramController.insert(actualRoot, moves, metaData));
    }

    public Optional<Diagram> getLast(Diagram diagram) {
        return switch (diagram.getNextDiagrams().size()) {
            case 0 -> Optional.of(diagram);
            case 1 -> getLast(diagram.getNextDiagrams().get(0));
            default -> Optional.empty();
        };
    }

    public void setActualNode(Diagram actualNode) {
        this.actualNode = actualNode;
    }

    public void setNewTree(Diagram tree) {
        this.actualNode = tree;
        games.setNewTree(tree.getRoot());
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

    public GamesRepository getGames() {
        return games;
    }

    public void expandIfLazy(Diagram diagram) {
        if (diagram.isLazy()) {
            games.update(diagramController.expand(diagram));
        }
    }
}
