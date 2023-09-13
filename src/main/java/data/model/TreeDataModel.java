package data.model;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;

public class TreeDataModel implements TreeModel {
    private Diagram actualNode;
    private final DiagramManager diagramManager=new DiagramManager();
    private final ArrayList<TreeModelListener> treeModelListeners = new ArrayList<>();

    public void setActualNode(Diagram actualNode) {
        this.actualNode = actualNode;
    }

    @Override
    public Object getRoot() {
        return actualNode.getRoot();
    }

    @Override
    public Object getChild(Object parent, int index) {
        return ((Diagram) parent).getNextDiagrams().get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        Diagram diagram=(Diagram) parent;
        if(diagram.isLazy()){
            diagramManager.expand(diagram);
        }
        return diagram.getNextDiagrams().size();
    }

    @Override
    public boolean isLeaf(Object node) {
        if (((Diagram) node).isLazy()) {
            return false;
        }
        return ((Diagram) node).getNextDiagrams().isEmpty();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        System.err.print("valueForPathChanged\n");
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return ((Diagram) parent).getNextDiagrams().indexOf((Diagram) child);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        treeModelListeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        treeModelListeners.remove(l);
    }

    public TreePath getTreePathTo(Diagram diagram) {
        return new TreePath(diagram.getPathFromRoot().toArray());
    }

    public void notifyListenersOnInsert(Diagram newDiagram) {
        Diagram parent = newDiagram.getParent().orElseThrow();

        int[] childrenArray = new int[1];
        Object[] objects = new Object[1];
        objects[0] = newDiagram;
        childrenArray[0] = parent.getNextDiagrams().indexOf(newDiagram);

        TreeModelEvent event = new TreeModelEvent(this,
                getTreePathTo(parent),
                childrenArray,
                objects
        );
        for (TreeModelListener listener : treeModelListeners) {
            listener.treeNodesInserted(event);
        }
    }

    public void notifyListenersOnNewTree(Diagram newDiagram) {
        TreeModelEvent event = new TreeModelEvent(this,
                getTreePathTo(newDiagram),
                null,
                null
        );
        for (TreeModelListener listener : treeModelListeners) {
            listener.treeStructureChanged(event);
        }
    }

}
