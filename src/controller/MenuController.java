package controller;

import vue.EditorWindow;
import vue.GameWindow;
import vue.MenuWindow;

import java.awt.*;
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
            game.setVisible(true);
        }

        if (ev.getSource() == window.getButtonLaunchEditor()) {
            boolean launchEditor = true;

            int width = 0;
            try {
                width = Integer.parseInt(
                        this.window.getTextFieldEditorWidthValue().getText()
                );
            } catch (final NumberFormatException e) {
                this.window.getLabelEditorStatus().setText("La largeur fournie est incorrecte.");
                launchEditor = false;
            }

            int height = 0;
            try {
                height = Integer.parseInt(
                        this.window.getTextFieldEditorHeightValue().getText()
                );
            } catch (final NumberFormatException e) {
                this.window.getLabelEditorStatus().setText("La hauteur fournie est incorrecte.");
                launchEditor = false;
            }

            if (launchEditor) {
                this.window.getLabelEditorStatus().setText("");
                this.window.setVisible(false);
                EditorWindow editor = new EditorWindow(this.window, width, height);
                editor.start();
            } else {
                this.window.getLabelEditorStatus().setBackground(Color.RED);
            }
        }
    }
}
