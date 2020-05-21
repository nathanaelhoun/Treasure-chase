package Vue;

import Vue.GameWindow;

/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 */
public class GoTreasureChase {
    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow(12, 12, 3);
        gameWindow.start();
    }
}
