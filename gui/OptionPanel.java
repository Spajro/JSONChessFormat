package gui;

import javax.swing.*;
import java.awt.*;

public class OptionPanel extends JPanel {
    private JTree jTree;
    private JScrollPane jScrollPane;
    public OptionPanel(Controller controller) {
        jTree=controller.createTreeWithDataModel();
        jTree.addMouseListener(new TreeMouseListener(jTree,controller));
        jScrollPane=new JScrollPane(jTree);
        jScrollPane.setPreferredSize(new Dimension(160,480));
        add(jScrollPane);
    }
}
