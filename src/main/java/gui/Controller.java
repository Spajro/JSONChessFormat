package gui;

import chess.Position;
import chess.utility.FENFactory;
import chess.utility.FENParser;
import data.pgn.PGNParser;
import data.annotations.ArrowAnnotation;
import data.annotations.FieldAnnotation;
import data.annotations.GraphicAnnotation;
import data.json.JsonFactory;
import data.model.*;
import chess.moves.RawMove;
import data.pgn.ParsedPGN;
import gui.board.BoardPanel;
import gui.board.SpecialKeysMap;
import gui.games.GamesFrame;
import gui.option.OptionPanel;
import gui.option.TreeMouseListener;
import log.Log;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Controller {
    private final DataModel dataModel;
    private final BoardPanel boardPanel;
    private final GamesFrame gamesFrame;
    private OptionPanel optionPanel;
    private TreeMouseListener treeMouseListener;
    private final FileManager fileManager = new FileManager();
    private final JsonFactory jsonFactory;

    public Controller(DataModel dataModel, BoardPanel boardPanel) {
        this.dataModel = dataModel;
        this.boardPanel = boardPanel;
        this.gamesFrame = new GamesFrame(dataModel, this);
        dataModel.setPromotionTypeProvider(getPromotionTypeProvider());
        jsonFactory = new JsonFactory(dataModel);
    }

    public void executeDragAction(RawMove move, SpecialKeysMap keysMap) {
        if (keysMap.isAnyPressed()) {
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
                        .getArrowAnnotations()
                        .add(new ArrowAnnotation(move, getDrawColor(keysMap)));
            }
        } else {
            dataModel.makeMove(move);
            boardPanel.setDiagram(dataModel.getActualNode());
            treeMouseListener.treeNodeInserted(dataModel.asTree().getTreePathTo(dataModel.getActualNode()));
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

    public void executeClickAction(Position position, SpecialKeysMap keysMap) {
        if (keysMap.isAnyPressed()) {
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
                        .getFieldAnnotations()
                        .add(new FieldAnnotation(position, getDrawColor(keysMap)));
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

    private GraphicAnnotation.DrawColor getDrawColor(SpecialKeysMap keysMap) {
        if (keysMap.areAnyTwoPressed()) {
            return GraphicAnnotation.DrawColor.YELLOW;
        } else if (keysMap.isControlPressed()) {
            return GraphicAnnotation.DrawColor.BLUE;
        } else if (keysMap.isAltPressed()) {
            return GraphicAnnotation.DrawColor.RED;
        } else if (keysMap.isShiftPressed()) {
            return GraphicAnnotation.DrawColor.GREEN;
        }
        throw new IllegalStateException("No special keys pressed when choosing annotation color");
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

    public int getScale() {
        return boardPanel.getScale();
    }

    public void setOptionPanel(OptionPanel optionPanel) {
        this.optionPanel = optionPanel;
    }

    public void setTextAnnotation(String text) {
        dataModel.getActualNode().getAnnotations().setTextAnnotation(text);
    }

    public void loadDataFromJSON(String filename) {
        try {
            dataModel.setNewTree(fileManager.loadJSON(filename));
            dataModel.asTree().notifyListenersOnNewTree(dataModel.getActualNode());
            boardPanel.setDiagram(dataModel.getActualNode());
        } catch (FileNotFoundException e) {
            Log.log().warn("file not found");
        }
    }

    public void loadDataFromPGN(String filename) {
        try {
            dataModel.setNewTree(fileManager.loadPGN(filename).diagram().orElseThrow());
            dataModel.asTree().notifyListenersOnNewTree(dataModel.getActualNode());
            boardPanel.setDiagram(dataModel.getActualNode());
        } catch (FileNotFoundException e) {
            Log.log().warn("file not found");
        }
    }

    public void saveDataToJSON(String filename) {
        fileManager.save(filename, jsonFactory.toJson());
    }

    public void loadChessBoardFromFEN(String fen) {
        dataModel.setNewTree(new Diagram(FENParser.getInstance().parseFEN(fen), null, 0));
        dataModel.asTree().notifyListenersOnNewTree(dataModel.getActualNode());
        boardPanel.setDiagram(dataModel.getActualNode());
    }

    public void saveChessBoardToFEN() {
        JTextArea textArea = new JTextArea();
        textArea.setText(FENFactory.getInstance().chessBoardToFEN(dataModel.getActualNode().getBoard()));
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(optionPanel, textArea);
    }

    public void makeMoves(String moves) {
        Optional<List<RawMove>> optionalRawMoves = PGNParser.getInstance().parseMoves(dataModel.getActualNode(), Arrays.asList(moves.split(" ")));
        optionalRawMoves.ifPresent(dataModel::makeMoves);
    }

    public void insertPGN(String filename) {
        try {
            ParsedPGN pgn = fileManager.loadPGN(filename);
            dataModel.insert(pgn.diagram().orElseThrow(), pgn.metadata());
            dataModel.asTree().notifyListenersOnNewTree(dataModel.getActualNode().getRoot());
            boardPanel.setDiagram(dataModel.getActualNode());
        } catch (FileNotFoundException e) {
            Log.log().warn("file not found");
        }
    }

    public void showGames() {
        gamesFrame.refresh();
        gamesFrame.setVisible(true);
    }

    public void selectGame(MetaData metaData) {
        Diagram node = dataModel.getGames().get(metaData);
        TreePath treePath = dataModel.asTree().getTreePathTo(node);
        optionPanel.getTree().setSelectionPath(treePath);
        optionPanel.getTree().scrollPathToVisible(treePath);
    }
}
