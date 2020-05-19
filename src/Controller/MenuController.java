package Controller;

import Vue.GameWindow;
import Vue.MenuWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Treasure chase menu
 *
 * @author Nathanaël Houn
 */
public class MenuController  implements ActionListener {

    private MenuWindow window;

    public MenuController(MenuWindow window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == window.buttonNewGame1) {
            this.window.setVisible(false);
            GameWindow game = new GameWindow(1, this.window);
            game.start();
        }
    }
}
