package dts;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.*;

public class DataModel implements TreeModel {
    private Board actualBoard;
    private Diagram tree;
    private String name;

    public DataModel() {
        tree=new Diagram();
        actualBoard=tree.getBoard();
        name="new datamodel";
    }

    public void readDiagramFromFile(String filename){
        tree=load(filename);
        name=filename;
    }

    public void makeMove(Move m){
        tree=tree.makeMove(m);
        setActualBoard(tree.getBoard());
    }

    Diagram load(String filename){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename+".bin"))) {
            return (Diagram) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    void save(){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(name+".bin"))) {
            outputStream.writeObject(tree.getOriginal());
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

    @Override
    public Object getRoot() {
        return tree.getOriginal();
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
