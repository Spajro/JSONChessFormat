package cli;

import chess.board.fields.Field;
import chess.formats.algebraic.AlgebraicUtility;
import chess.formats.algebraic.LongAlgebraicParser;
import data.model.DataModel;
import data.model.diagrams.Diagram;
import chess.Position;
import chess.moves.raw.RawMove;
import data.file.FileManager;
import data.pgn.ParsedPGN;
import log.Log;
import log.TimeLogRunnable;
import log.TimeLogSupplier;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class CommandLineHandler {
    private final DataModel dataModel = new DataModel();
    private Diagram node = dataModel.getActualNode();
    private final FileManager fileManager = new FileManager();
    private final AlgebraicUtility algebraicUtility = AlgebraicUtility.getInstance();
    private final LongAlgebraicParser longAlgebraicParser = new LongAlgebraicParser();

    public void handle(String data) {
        if (data.isEmpty()) {
            return;
        }
        ArrayDeque<String> input = new ArrayDeque<>(List.of(data.split(" ")));
        if (input.isEmpty()) {
            System.out.println("Empty input");
            return;
        }
        switch (input.poll()) {
            case "load" -> load(input.poll());
            case "save" -> fileManager.save(input.poll(), dataModel);
            case "move" -> makeMove(longAlgebraicParser.parseLongAlgebraic(input.poll(), node.getBoard().getColor()));
            case "delete" -> deleteDiagram();
            case "farBack" -> goBack(Integer.parseInt(input.poll()));
            case "moves" -> printMoves();
            case "history" -> printHistory();
            case "back" -> jumpBack();
            case "forward" -> jumpForward();
            case "help" -> printHelp();
            case "insert" -> insertPgn(input.poll());
            case "status" -> printStatus();
            case "quit" -> System.exit(0);
            default -> System.out.print("Unknown input");
        }
        display();
    }

    private void load(String filename) {
        Optional<Diagram> optionalDiagram = new TimeLogSupplier<Optional<Diagram>>(
                () -> {
                    try {
                        Diagram diagram = fileManager.loadJSON(filename);
                        dataModel.setNewTree(diagram);
                        return new TimeLogSupplier.SizedResult<>(dataModel.getGames().size(), Optional.of(diagram));
                    } catch (FileNotFoundException e) {
                        return new TimeLogSupplier.SizedResult<>(1, Optional.empty());
                    }
                },
                "Loading time: "
        ).apply();
        if (optionalDiagram.isPresent()) {
            node = optionalDiagram.get();
        } else {
            Log.log().fail("Loading failed");
        }
    }

    private void makeMove(RawMove rawMove) {
        dataModel.makeMove(rawMove);
        node=dataModel.getActualNode();
    }

    private void deleteDiagram() {
        if (node.getParent().isPresent()) {
            Diagram Temp = node;
            node = node.getParent().get();
            node.getNextDiagrams().remove(Temp);
        } else {
            System.out.print("Cant delete");
        }
    }

    private void goBack(int id) {
        while (id > 0) {
            id--;
            if (node.getParent().isPresent()) {
                node = node.getParent().get();
            } else {
                break;
            }
        }
    }

    private void printMoves() {
        node.getNextDiagrams()
                .stream()
                .map(Diagram::getMoveName)
                .forEach(string -> System.out.print(string + " "));
    }

    private void printHistory() {
        node.getPathFromRoot().stream()
                .map(Diagram::getMoveName)
                .forEach(string -> System.out.print(string + " "));
    }

    private void jumpBack() {
        if (node.getParent().isPresent()) {
            node = node.getParent().get();
        } else {
            System.out.print("Cant jump back");
        }
    }

    private void jumpForward() {
        if (!node.getNextDiagrams().isEmpty()) {
            node = node.getNextDiagrams().get(0);
        } else {
            System.out.print("Cant jump forward");
        }
    }

    void printHelp() {
        System.out.print("TODO");
    }

    private void display() {
        System.out.println();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                Field field = node.getBoard().getField(Position.of(j, i));
                if (field.isEmpty()) {
                    System.out.print("00");
                } else {
                    if (field.getPiece().getColor().isWhite()) {
                        System.out.print("W");
                    } else {
                        System.out.print("B");
                    }
                    System.out.print(algebraicUtility.typeToAlgebraic(field.getPiece().getType()));
                }
                System.out.print(" ");
            }
            System.out.println();
            System.out.println();
        }
    }

    private void insertPgn(String filename) {
        try {
            Iterator<ParsedPGN> pgnParser = fileManager.loadPagedPGN(filename);
            new TimeLogRunnable(
                    () -> {
                        int size = 0;
                        while (pgnParser.hasNext()) {
                            ParsedPGN parsedPGN = pgnParser.next();
                            if (parsedPGN.moves().isEmpty()) {
                                Log.log().warn("Unable to parse game: " + parsedPGN.metadata().toString());
                            } else {
                                dataModel.insert(parsedPGN.moves().get(), parsedPGN.metadata());
                                size++;
                            }
                        }
                        return size;
                    },
                    "Inserting time: "
            ).apply();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void printStatus() {
        System.out.println("Nodes: " + size(node.getRoot()));
        System.out.println("Games: " + dataModel.getGames().getGameData().size());
    }

    private int size(Diagram diagram) {
        if (diagram.isLazy()) {
            return 1;
        }
        return 1 + diagram.getNextDiagrams().stream().mapToInt(this::size).sum();
    }
}
