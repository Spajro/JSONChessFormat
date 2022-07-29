package src.data.dts;

import src.data.dts.board.Board;
import src.data.dts.board.ChessBoard;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.*;
import java.util.LinkedList;

public class DataModel implements TreeModel {
    private ChessBoard actualBoard;
    private Diagram actualNode;
    private String name;

    private final LinkedList<TreeModelListener> treeModelListeners;

    public DataModel() {
        treeModelListeners = new LinkedList<>();
        actualNode = new Diagram();
        actualBoard = actualNode.getBoard();
        name = "new datamodel";
    }

    public void loadDataFromFile(String filename) {
        actualNode = load(filename);
        name = filename;
    }

    public void saveDataToFile(String filename) {
        save(filename);
    }

    public void makeMove(Move m) {
        actualNode = actualNode.makeMove(m);
        setActualBoard(actualNode.getBoard());
        notifyListenersOnInsert(actualNode);
    }

    public TreePath getTreePathTo(Diagram diagram) {
        return new TreePath(diagram.getPathFromOriginal().toArray());
    }

    private Diagram load(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename + ".bin"))) {
            return (Diagram) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void save(String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename + ".bin"))) {
            outputStream.writeObject(actualNode.getOriginal());
            System.out.print("Saved sukces");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ChessBoard getActualBoard() {
        return actualBoard;
    }

    public void setActualBoard(ChessBoard actualBoard) {
        this.actualBoard = actualBoard;
    }

    public void setActualNode(Diagram actualNode) {
        this.actualNode = actualNode;
    }

    @Override
    public Object getRoot() {
        return actualNode.getOriginal();
    }

    public Diagram getActualNode() {
        return actualNode;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return ((Diagram) parent).getNextDiagram(index);
    }

    @Override
    public int getChildCount(Object parent) {
        return ((Diagram) parent).getNextDiagramsCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        return ((Diagram) node).getNextDiagramsCount() == 0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        System.err.print("valueForPathChanged\n");
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return ((Diagram) parent).getIndexInNextDiagrams((Diagram) child);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        treeModelListeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        treeModelListeners.remove(l);
    }

    public void notifyListenersOnInsert(Diagram newDiagram) {
        int[] childrenArray = new int[1];
        Object[] objects = new Object[1];
        objects[0] = newDiagram;
        childrenArray[0] = newDiagram.getParent().getIndexInNextDiagrams(newDiagram);
        TreeModelEvent event = new TreeModelEvent(this,
                getTreePathTo(newDiagram.getParent()),
                childrenArray,
                objects
        );
        for (TreeModelListener listener : treeModelListeners) {
            listener.treeNodesInserted(event);
        }
    }

}
