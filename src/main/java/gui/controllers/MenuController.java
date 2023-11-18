package gui.controllers;

import chess.formats.fen.FENFactory;
import chess.formats.fen.FENParser;
import data.file.FileManager;
import data.model.DataModel;
import data.model.diagrams.Diagram;
import data.model.diagrams.FENDiagram;
import data.model.metadata.GameData;
import data.model.metadata.MetaData;
import data.pgn.ParsedPGN;
import gui.board.BoardPanel;
import gui.games.GamesFrame;
import gui.option.OptionPanel;
import log.Log;
import log.TimeLogRunnable;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

public class MenuController {
    private final DataModel dataModel;
    private final FileManager fileManager;
    private final BoardPanel boardPanel;
    private OptionPanel optionPanel;
    private final GamesFrame gamesFrame;

    MenuController(DataModel dataModel, BoardPanel boardPanel) {
        this.dataModel = dataModel;
        this.boardPanel = boardPanel;
        this.gamesFrame = new GamesFrame(dataModel, this);
        this.fileManager = new FileManager();
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
            List<ParsedPGN> parsedPGNS = fileManager.loadPGN(filename);
            if (parsedPGNS.isEmpty()) {
                Log.log().warn("Empty pgn");
                return;
            }
            dataModel.setNewTree(new Diagram());
            dataModel.asTree().notifyListenersOnNewTree(dataModel.getActualNode());
            boardPanel.setDiagram(dataModel.getActualNode());
            if (parsedPGNS.size() > 1) {
                for (int i = 1; i < parsedPGNS.size(); i++) {
                    dataModel.insert(parsedPGNS.get(i).moves().orElseThrow(), parsedPGNS.get(i).metadata());
                    dataModel.asTree().notifyListenersOnNewTree(dataModel.getActualNode().getRoot());
                }
            }
        } catch (FileNotFoundException e) {
            Log.log().warn("file not found");
        }
    }

    public void saveDataToJSON(String filename) {
        fileManager.save(filename, dataModel);
    }

    public void export(String filename, GameData gameData) {
        fileManager.export(filename, dataModel.getGames().get(gameData), gameData);
    }

    public void loadChessBoardFromFEN(String fen) {
        dataModel.setNewTree(new FENDiagram(null, FENParser.getInstance().parseFEN(fen)));
        dataModel.asTree().notifyListenersOnNewTree(dataModel.getActualNode());
        boardPanel.setDiagram(dataModel.getActualNode());
    }

    public void saveChessBoardToFEN() {
        JTextArea textArea = new JTextArea();
        textArea.setText(FENFactory.getInstance().chessBoardToFEN(dataModel.getActualNode().getBoard()));
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(optionPanel, textArea);
    }

    public void insertPGN(String filename) {
        try {
            Iterator<ParsedPGN> pgnParser = fileManager.loadPagedPGN(filename);
            new TimeLogRunnable(
                    () -> {
                        int size = 0;
                        while (pgnParser.hasNext()) {
                            ParsedPGN parsedPGN = pgnParser.next();
                            dataModel.insert(parsedPGN.moves().orElseThrow(), parsedPGN.metadata());
                            size++;
                        }
                        boardPanel.setDiagram(dataModel.getActualNode());
                        dataModel.asTree().notifyListenersOnNewTree(dataModel.getActualNode().getRoot());
                        return size;
                    },
                    "Inserting time: "
            ).apply();
        } catch (FileNotFoundException e) {
            Log.log().warn("file not found");
        }
    }

    public void showGames() {
        gamesFrame.refresh();
        gamesFrame.setVisible(true);
    }

    public void selectGame(MetaData metaData) {
        selectNode(dataModel.getGames().get(metaData));
    }

    public void selectGameEnd(MetaData metaData) {
        selectNode(dataModel.getLast(dataModel.getGames().get(metaData)).orElseThrow());
    }

    private void selectNode(Diagram node) {
        TreePath treePath = dataModel.asTree().getTreePathTo(node);
        optionPanel.getTree().setSelectionPath(treePath);
        optionPanel.getTree().scrollPathToVisible(treePath);
    }

    public void setOptionPanel(OptionPanel optionPanel) {
        this.optionPanel = optionPanel;
    }
}
