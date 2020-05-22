package controller;

import model.Board;
import model.Cell;
import model.Hunter;
import model.Position;
import vue.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import static vue.MenuWindow.*;

/**
 * Treasure chase
 * <p>
 * Controller.GameController part of MVC
 *
 * @author Nathanaël Houn
 */
public class GameController implements ActionListener {

    private final GameWindow window;
    private final Board board;

    public GameController(GameWindow window, Board board) {
        this.window = window;
        this.board = board;
    }

    public int getBoardHeight() {
        return board.HEIGHT;
    }

    public int getBoardWidth() {
        return board.WIDTH;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == this.window.getButtonNextRound()) {
            this.window.getButtonNextRound().setEnabled(false);

            HashMap<Position, Position> moves = this.board.doRound();
            this.updateCellsLabels(moves);
            if (this.window.getScrollPaneStatus().isVisible()) {
                this.updateStatusLabel();
            }

            if (!this.board.isWinner()) {
                this.window.getButtonNextRound().setEnabled(true);
            }
            return;
        }

        if (ev.getSource() == this.window.getButtonHideShowStatus()) {
            if (this.window.getScrollPaneStatus().isVisible()) {
                this.window.getScrollPaneStatus().setVisible(false);
                this.window.getButtonHideShowStatus().setText("Afficher les détails");
                this.window.getPanelStatus().setPreferredSize(new Dimension(
                        this.window.getSize().width - 20,
                        this.window.getPanelStatus().getSize().height - this.window.getScrollPaneStatus().getPreferredSize().height
                ));

            } else {
                this.updateStatusLabel();
                this.window.getScrollPaneStatus().setVisible(true);
                this.window.getButtonHideShowStatus().setText("Cacher les détails");
                this.window.getPanelStatus().setPreferredSize(new Dimension(
                        this.window.getSize().width - 20,
                        this.window.getPanelStatus().getSize().height + this.window.getScrollPaneStatus().getPreferredSize().height
                ));
            }
            return;
        }

        if (ev.getSource() == this.window.getButtonGoToMenu()) {
            this.window.getMenu().setVisible(true);
            this.window.dispose();
        }
    }

    /**
     * Update all the cells labels from the model
     *
     * @param moves HashMap<Position, Position> Array of moves, the first position is the old one, and the second is the new one
     */
    public void updateCellsLabels(HashMap<Position, Position> moves) {

        for (Map.Entry<Position, Position> move : moves.entrySet()) {
            Position originPosition = move.getKey();
            Position destinationPosition = move.getValue();

            // minus one because the JLabels arrays doesn't count the borders
            JLabel originJLabel = this.window.getCellLabel(originPosition.getX() - 1, originPosition.getY() - 1);
            JLabel destinationJLabel = this.window.getCellLabel(destinationPosition.getX() - 1, destinationPosition.getY() - 1);

            Cell originCell = this.board.getCell(originPosition.getX(), originPosition.getY());
            Cell destinationCell = this.board.getCell(destinationPosition.getX(), destinationPosition.getY());

            if (originCell.toString().equals("·")) {
                // else : there is a Hunter on this cell, don't overwrite him
                originJLabel.setBackground(COLOR_CELL_FREE);
                originJLabel.setText("·");
            }

            if (this.board.isWinner() && this.board.getTreasure() == destinationCell) {
                destinationJLabel.setBackground(COLOR_CELL_HUNTER_WINNER);
            } else {
                destinationJLabel.setBackground(COLOR_CELL_HUNTER);
            }
            destinationJLabel.setText(destinationCell.toString());
        }
    }

    /**
     * Update all the cells labels from the model
     */
    public void initialiseCellLabels() {
        for (int y = 0; y < this.board.HEIGHT; y++) {
            for (int x = 0; x < this.board.WIDTH; x++) {

                JLabel labelToUpdate = this.window.getCellLabel(x, y);

                Cell cell = this.board.getCell(x + 1, y + 1);

                switch (cell.toString()) {
                    case "·":
                        labelToUpdate.setBackground(COLOR_CELL_FREE);
                        labelToUpdate.setText("·");
                        break;

                    case "#":
                        labelToUpdate.setBackground(COLOR_CELL_STONE);
                        break;

                    case "T":
                        labelToUpdate.setBackground(COLOR_CELL_TREASURE);
                        labelToUpdate.setText("T");
                        break;

                    default:
                        labelToUpdate.setBackground(COLOR_CELL_HUNTER);
                        labelToUpdate.setText(cell.toString());
                }
            }
        }
    }

    /**
     * Update the status label with the status of each player
     */
    public void updateStatusLabel() {
        StringBuilder str = new StringBuilder();

        String newline = System.getProperty("line.separator");
        for (Hunter h : this.board.getHunters()) {
            str.append("Personnage ").append(h.toString()).append(" : ");

            if (this.board.getTreasure().getWinner() == h) {
                str.append(" ** WINNER ** ");
            } else {
                str.append("Model.Position actuelle :  ").
                        append(h.getCurrentCell().getPosition().toString()).
                        append(" | Meilleure direction : ").
                        append(h.getDirection().toString());
            }

            str.append(newline);
        }

        this.window.getLabelStatus().setText(str.toString());
    }
}
