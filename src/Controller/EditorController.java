package Controller;

import Model.*;
import Vue.EditorWindow;
import Vue.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Treasure chase Editor
 *
 * @author Nathanaël Houn
 */

public class EditorController implements ActionListener {
    private EditorWindow window;

    private Board board;

    public EditorController(EditorWindow window, int boardWidth, int boardHeight) {
        this.window = window;
        this.board = new Board(boardWidth, boardHeight);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == this.window.getButtonReturnToMenu()) {
            this.window.getMenu().setVisible(true);
            this.window.dispose();
        }

        if (ev.getSource() == this.window.getButtonLaunchGame()) {
            String buildStatus = this.buildBoard();

            if (buildStatus.length() == 0) {
                this.window.setVisible(false);
                GameWindow game = new GameWindow(this.window.getMenu(), this.board);
                game.start();
                this.window.dispose();
            } else {
                this.window.setErrorLabel(buildStatus);
            }
        }
    }

    /**
     * Build the board from the JLabel array
     * <p>
     * Scan the JLabels array colors to find which Cell it which
     * and replace it in the current Board attribute
     * Then initialize all useful information to make the board playable
     * <p>
     * If there is an error, return the error in a string.
     *
     * @return a string with the error, or an empty string
     */
    private String buildBoard() {
        for (int y = 1; y <= this.board.getBoardHeight(); y++) {
            for (int x = 1; x <= this.board.getBoardWidth(); x++) {
                JLabel currentLabel = this.window.getCellLabels().get(y).get(x);
                Color background = currentLabel.getBackground();
                if (Color.RED.equals(background) || Color.LIGHT_GRAY.equals(background)) {
                    // This is a CellSide or an empty cellFree: already defined
                    continue;
                }

                if (Color.YELLOW.equals(background)) {
                    this.board.replaceCell(x, y, new CellTreasure(new Position(x, y)));
                } else if (Color.GRAY.equals(background)) {
                    Hunter newHunter = this.board.addHunter();
                    ((CellFree) this.board.getCell(x, y)).setHunter(newHunter);
                    newHunter.setCurrentCell(this.board.getCell(x, y));
                } else if (Color.BLUE.equals(background)) {
                    this.board.replaceCell(x, y, new CellStone(new Position(x, y), CellStone.Orientation.HORIZONTAL)); // TODO : trouver la bonne orientation
                } else {
                    System.err.println("Erreur lors du parcours du tableau de l'éditeur à la case (" + x + ", " + y + "). Couleur inconnue : " + currentLabel.getBackground());
                    return "Case inconnue : (" + x + ", " + y + ").";
                }
            }

        }

        if (this.board.findAndSetTreasure() == false) {
            return "Il n'y a pas de trésor sur ce terrain.";
        }

        for (int y = 1; y <= this.board.getBoardHeight(); y++) {
            for (int x = 1; x <= this.board.getBoardWidth(); x++) {
                Cell currentCell = this.board.getCell(x, y);

                if (currentCell != this.board.getTreasure() && currentCell.toString().equals("T")) {
                    return "Il y a plus d'un trésor sur ce terrain.";
                }
            }
        }

        if (this.board.getHunters().size() <= 0) {
            return "Il n'y a pas de joueurs sur le terrain.";
        }

        return "";
    }
}
