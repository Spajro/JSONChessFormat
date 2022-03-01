package gui;

import dts.DataModel;

import javax.swing.*;
import java.awt.*;

public class OptionPanel extends JPanel {
    private JTree jTree;
    private JScrollPane jScrollPane;
    public OptionPanel(DataModel dataModel) {
        jTree=new JTree(dataModel);
        jTree.addMouseListener(new TreeMouseListener(jTree,dataModel));
        jScrollPane=new JScrollPane(jTree);
        jScrollPane.setPreferredSize(new Dimension(160,480));
        add(jScrollPane);
    }
}
