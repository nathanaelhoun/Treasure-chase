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

    static final int MAX_BOARD_SURFACE = 2500;

    private final MenuWindow window;
    private final String ORIGINAL_EDITOR_STATUS_TEXT;

    public MenuController(MenuWindow window) {
        this.window = window;
        this.ORIGINAL_EDITOR_STATUS_TEXT = this.window.getTextPaneEditorStatus().getText();
        this.window.getLabelMaximumValue().setText("Créer son propre terrain, de taille maximum " +  MenuController.MAX_BOARD_SURFACE);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == window.getButtonNewGame1()) {
            this.window.setVisible(false);
            GameWindow game = new GameWindow(this.window, 1);
            game.setVisible(true);
        }

        if (ev.getSource() == window.getButtonQuit()) {
            System.exit(0);
        }

        if (ev.getSource() == window.getButtonLaunchEditor()) {
            String newline = System.getProperty("line.separator");
            String errorStr = "";

            int width = 0;
            try {
                width = Integer.parseInt(
                        this.window.getTextFieldEditorWidthValue().getText()
                );

                if (width <= 5) {
                    errorStr += "La largeur doit être au moins égale à 5." + newline;
                }
            } catch (final NumberFormatException e) {
                errorStr += "La largeur fournie est incorrecte." + newline;
            }

            int height = 0;
            try {
                height = Integer.parseInt(
                        this.window.getTextFieldEditorHeightValue().getText()
                );

                if (height <= 5) {
                    errorStr += "La hauteur doit être au moins égale à 5." + newline;
                }
            } catch (final NumberFormatException e) {
                errorStr += "La hauteur fournie est incorrecte." + newline;
            }

            if (errorStr.equals("") && width * height > MAX_BOARD_SURFACE) {
                errorStr = "La grille demandée est trop grande." + newline;
            }

            if (errorStr.equals("")) {
                this.window.getTextPaneEditorStatus().setText(ORIGINAL_EDITOR_STATUS_TEXT);
                this.window.getTextPaneEditorStatus().setBackground(MenuWindow.COLOR_BG_DEFAULT);
                this.window.setVisible(false);
                EditorWindow editor = new EditorWindow(this.window, width, height);
                editor.start();
            } else {
                this.window.getTextPaneEditorStatus().setBackground(MenuWindow.COLOR_BG_ERROR);
                this.window.getTextPaneEditorStatus().setText(errorStr.substring(0, errorStr.length() - 1));
            }
        }
    }
}
