package gui.option;

import gui.Controller;

import javax.swing.*;

public class OptionPanel extends JPanel {
    private final JTree jTree;
    private final JTextArea jTextArea;

    public OptionPanel(Controller controller) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        jTree = controller.createTreeWithDataModel();
        jTree.addMouseListener(new TreeMouseListener(jTree, controller));

        jTextArea = new JTextArea();

        jTextArea.getDocument().addDocumentListener(new TextAreaListener(controller));

        add(new JScrollPane(jTree));
        add(new JScrollPane(jTextArea));
    }

    public void setText(String text) {
        jTextArea.setText(text);
    }

    public JTree getTree() {
        return jTree;
    }
}
