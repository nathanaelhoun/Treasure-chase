package controller;

import vue.EditorWindow;
import vue.GameWindow;
import vue.MenuWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Treasure chase menu
 *
 * @author Nathanaël Houn
 */
public class MenuController implements ActionListener {

    private final MenuWindow window;

    public MenuController(MenuWindow window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == window.getButtonNewGame1()) {
            this.window.setVisible(false);
            GameWindow game = new GameWindow(this.window, 1);
            game.start();
        }

        if (ev.getSource() == window.getButtonLaunchEditor()) {
            int width = Integer.parseInt(this.window.getTextFieldEditorWidth().getValue().toString());
            int height = Integer.parseInt(this.window.getTextFieldEditorHeight().getValue().toString());

            this.window.setVisible(false);
            EditorWindow editor = new EditorWindow(this.window, width, height);
            editor.start();
        }
    }
}
