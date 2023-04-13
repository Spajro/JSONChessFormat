package data.model;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.LinkedList;

public class TreeDataModel implements TreeModel {
    private Diagram actualNode;
    private final LinkedList<TreeModelListener> treeModelListeners = new LinkedList<>();

    public void setActualNode(Diagram actualNode) {
        this.actualNode = actualNode;
    }

    @Override
    public Object getRoot() {
        return actualNode.getRoot();
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

    public TreePath getTreePathTo(Diagram diagram) {
        return new TreePath(diagram.getPathFromRoot().toArray());
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
