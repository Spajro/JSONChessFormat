package dts;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.*;

public class DataModel implements TreeModel {
    private Board actualBoard;
    private Diagram actualNode;
    private String name;

    public DataModel() {
        actualNode =new Diagram();
        actualBoard= actualNode.getBoard();
        name="new datamodel";
    }

    public void readDataFromFile(String filename){
        actualNode =load(filename);
        name=filename;
    }

    public void saveDataToFile(){
        save();
    }

    public void makeMove(Move m){
        actualNode = actualNode.makeMove(m);
        setActualBoard(actualNode.getBoard());
    }

    public TreePath getTreePathTo(Diagram diagram){
        return new TreePath(diagram.getPathFromOriginal().toArray());
    }
    private Diagram load(String filename){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename+".bin"))) {
            return (Diagram) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void save(){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(name+".bin"))) {
            outputStream.writeObject(actualNode.getOriginal());
            System.out.print("Saved sukces");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public Board getActualBoard() {
        return actualBoard;
    }

    public void setActualBoard(Board actualBoard) {
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
        return ((Diagram) node).getNextDiagramsCount()==0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return ((Diagram) parent).getIndexInNextDiagrams((Diagram) child);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }
}
