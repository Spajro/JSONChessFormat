import data.model.DataModel;
import gui.App;

import javax.swing.*;

public class GuiMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App(new DataModel()));
    }
}
