package gui;

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

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_CONTROL){
            controller.setEditingAnnotation(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_CONTROL){
            controller.setEditingAnnotation(false);
        }
    }
}
