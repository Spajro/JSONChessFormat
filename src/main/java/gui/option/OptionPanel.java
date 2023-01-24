package gui.option;

import gui.Controller;

import javax.swing.*;
import java.awt.*;

public class OptionPanel extends JPanel {
    private final JTree jTree;
    private final JScrollPane jScrollPane;
    private final JTextArea jTextArea;
    public OptionPanel(Controller controller) {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        jTree=controller.createTreeWithDataModel();
        jTree.addMouseListener(new TreeMouseListener(jTree,controller));

        jScrollPane=new JScrollPane(jTree);

        jTextArea=new JTextArea();
        jTextArea.getDocument().addDocumentListener(new TextAreaListener(controller));

        add(jScrollPane);
        add(jTextArea);
    }

    public void setText(String text){
        jTextArea.setText(text);
    }
}
