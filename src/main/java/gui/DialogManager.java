package gui;

import javax.swing.*;

public class DialogManager {
    private final JFrame frame;

    public DialogManager(JFrame frame) {
        this.frame = frame;
    }

    public String getFilename() {
        return JOptionPane.showInputDialog(frame, "What is name of file?");
    }

    public String getFEN() {
        return JOptionPane.showInputDialog(frame, "Write FEN you want to import");
    }

    public String getMoves() {
        return JOptionPane.showInputDialog(frame, "Write moves you want to add");
    }
}
