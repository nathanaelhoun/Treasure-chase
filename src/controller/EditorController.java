package controller;

import model.*;
import vue.EditorWindow;
import vue.GameWindow;
import vue.MenuWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static vue.MenuWindow.*;

/**
 * Treasure chase Editor
 *
 * @author Nathanaël Houn
 */

public class EditorController implements ActionListener {
    private final EditorWindow window;

    private final Board board;

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
                game.setVisible(true);
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
        // Cleanup precedent board
        this.board.cleanup();

        // Build the new board
        for (int y = 1; y <= this.board.HEIGHT; y++) {
            for (int x = 1; x <= this.board.WIDTH; x++) {
                JLabel currentLabel = this.window.getCellLabels().get(y).get(x);
                Color background = currentLabel.getBackground();

                Position currentPosition = this.board.getCell(x, y).getPosition(); // don't reallocate position

                if (COLOR_CELL_FREE.equals(background)) {
                    // This is an empty cellFree: should be already defined.
                    // We check that there is no another thing on this cell
                    if (this.board.getCell(x, y).toString() != "·") {
                        this.board.replaceCell(x, y, new CellFree(currentPosition));
                    }
                    continue;
                }

                if (COLOR_CELL_TREASURE.equals(background)) {
                    this.board.replaceCell(x, y, new CellTreasure(currentPosition));

                } else if (COLOR_CELL_HUNTER.equals(background)) {
                    Hunter newHunter = this.board.addHunter("");
                    ((CellFree) this.board.getCell(x, y)).setCurrentHunter(newHunter);
                    newHunter.setCurrentCell(this.board.getCell(x, y));

                } else if (COLOR_CELL_STONE.equals(background)) {

                    CellStone.Orientation detectedOrientation = null;

                    // Detect the orientation of the wall with the adjacent labels
                    for (Direction dir : Direction.getCardinalPoints()) {
                        Position testedPos = currentPosition.computePosition(dir);

                        if (testedPos.getX() == 0 ||
                                testedPos.getX() == this.board.WIDTH + 1 ||
                                testedPos.getY() == 0 ||
                                testedPos.getY() == this.board.HEIGHT + 1
                        ) {
                            return "Les murs ne peuvent pas être adjacents aux bords. Erreur à la case (" + x + ", " + y + ").";
                        }

                        JLabel testedLabel = this.window.getCellLabels().get(testedPos.getY()).get(testedPos.getX());

                        if (testedLabel.getBackground().equals(COLOR_CELL_STONE)) {
                            if (detectedOrientation == null) {
                                detectedOrientation = orientationFromCardinalDirection(dir);
                            } else if (!detectedOrientation.equals(orientationFromCardinalDirection(dir))) {
                                return "Deux murs ne peuvent être adjacents. Erreur à la case (" + x + ", " + y + ").";
                            }
                        }
                    }

                    if (detectedOrientation == null) {
                        // By default (if there is only one CellStone), we set it to vertical
                        detectedOrientation = CellStone.Orientation.VERTICAL;
                    }

                    this.board.replaceCell(x, y, new CellStone(currentPosition, detectedOrientation, this.board));

                } else {
                    System.err.println("Erreur lors du parcours du tableau de l'éditeur à la case (" + x + ", " + y + "). Couleur inconnue : " + currentLabel.getBackground());
                    return "Case inconnue : (" + x + ", " + y + ").";
                }
            }

        }

        if (!this.board.findAndSetTreasure()) {
            return "Il n'y a pas de trésor sur ce terrain.";
        }

        for (int y = 1; y <= this.board.HEIGHT; y++) {
            for (int x = 1; x <= this.board.WIDTH; x++) {
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

    /**
     * Convert a cardinal direction to an orientation (e.g. SOUTH => VERTICAL)
     *
     * @param dir the direction
     * @return the orientation
     */
    private CellStone.Orientation orientationFromCardinalDirection(Direction dir) {
        switch (dir) {

            case EAST:
            case WEST:
                return CellStone.Orientation.HORIZONTAL;

            case SOUTH:
            case NORTH:
                return CellStone.Orientation.VERTICAL;

            case NORTH_EAST:
            case NORTH_WEST:
            case SOUTH_WEST:
            case SOUTH_EAST:
                assert (false);
        }

        return null;
    }
}
