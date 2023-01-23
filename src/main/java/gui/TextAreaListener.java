package gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class TextAreaListener implements DocumentListener {
    private final Controller controller;

    public TextAreaListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        setTextAnnotation(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        setTextAnnotation(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        setTextAnnotation(e);
    }

    private void setTextAnnotation(DocumentEvent e) {
        try {
            controller.setTextAnnotation(e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }
}
