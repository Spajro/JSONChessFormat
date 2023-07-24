package gui.controllers;

import chess.moves.valid.executable.ExecutableMove;
import chess.utility.FENFactory;
import chess.utility.FENParser;
import chess.utility.ShortAlgebraicParser;
import data.MoveParser;
import data.file.FileManager;
import data.json.JsonFactory;
import data.model.DataModel;
import data.model.Diagram;
import data.model.FENDiagram;
import data.model.metadata.MetaData;
import data.pgn.ParsedPGN;
import gui.board.BoardPanel;
import gui.games.GamesFrame;
import gui.option.OptionPanel;
import log.Log;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class MenuController {
    private final DataModel dataModel;
    private final FileManager fileManager;
    private final BoardPanel boardPanel;
    private OptionPanel optionPanel;
    private final GamesFrame gamesFrame;
    private final ShortAlgebraicParser shortAlgebraicParser = new ShortAlgebraicParser();

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
            if (parsedPGNS.size() == 0) {
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
        fileManager.save(filename, new JsonFactory(dataModel).toJson());
    }

    public void loadChessBoardFromFEN(String fen) {
        dataModel.setNewTree(new FENDiagram(null, null, FENParser.getInstance().parseFEN(fen)));
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
        Optional<ArrayDeque<ExecutableMove>> optionalMoves = MoveParser.getInstance()
                .parseMoves(
                        dataModel.getActualNode().getBoard(),
                        List.of(moves.split(" ")),
                        shortAlgebraicParser::parseShortAlgebraic);
        optionalMoves.ifPresent(dataModel::insertInPlace);
    }

    public void insertPGN(String filename) {
        try {
            Iterator<ParsedPGN> pgnParser = fileManager.loadPagedPGN(filename);
            long startTime = System.nanoTime();
            long size = 0;
            while (pgnParser.hasNext()) {
                ParsedPGN parsedPGN = pgnParser.next();
                dataModel.insert(parsedPGN.moves().orElseThrow(), parsedPGN.metadata());
                size++;
            }
            boardPanel.setDiagram(dataModel.getActualNode());
            dataModel.asTree().notifyListenersOnNewTree(dataModel.getActualNode().getRoot());
            long endTime = System.nanoTime();
            long nanoDuration = (endTime - startTime);
            double secondDuration = ((double) nanoDuration / Math.pow(10, 9));
            double nodesPerSec = size / secondDuration;
            Log.debug("Inserting time: " + secondDuration + "s with speed: " + nodesPerSec + "gps"); //TODO FOR DEBUG
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
