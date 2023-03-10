package gui;

import log.Log;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
    private final Controller controller;

    public KeyboardListener(Controller controller) {
        this.controller = controller;
        controller.setKeyboardListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'z') {
            if (!controller.isEditingAnnotation()) {
                Log.log().info("Start Editing Annotation");
                controller.setEditingAnnotation(true);
            } else {
                Log.log().info("Stop Editing Annotation");
                controller.setEditingAnnotation(false);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
