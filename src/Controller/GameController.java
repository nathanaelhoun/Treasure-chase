package Controller;

import Model.Board;
import Model.Cell;
import Model.Hunter;
import Vue.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Treasure chase
 * <p>
 * Controller.GameController part of MVC
 *
 * @author Nathanaël Houn
 */
public class GameController implements ActionListener {

    private final GameWindow gameWindow;
    private Board board;

    public GameController(GameWindow window, Board board) {
        this.gameWindow = window;
        this.board = board;
    }

    public int getBoardHeight() {
        return this.board.getBoardHeight();
    }

    public int getBoardWidth() {
        return this.board.getBoardWidth();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == this.gameWindow.getButtonNextRound()) {
            this.board.doRound();
            this.updateCellsLabels();
            this.updateStatusLabel();

            if(this.board.isWinner()) {
                this.gameWindow.getButtonNextRound().setEnabled(false);
            }

            return;
        }

        if (ev.getSource() == this.gameWindow.getButtonGoToMenu()) {
            this.gameWindow.getMenu().setVisible(true);
            this.gameWindow.dispose();
        }

//        if (ev.getSource() == this.gameWindow.getButtonNewGame()) {
//            this.board = initiateBoard();
//            this.updateCellsLabels();
//            this.updateStatusLabel();
//
//            this.gameWindow.getButtonNextRound().setEnabled(true);
//            return;
//        }

    }

    /**
     * Update all the cells labels from the model
     */
    public void updateCellsLabels() {
        for (int y = 0; y < this.board.getBoardHeight(); y++) {
            for (int x = 0; x < this.board.getBoardWidth(); x++) {

                JLabel labelToUpdate = this.gameWindow.getCellLabel(x, y);

                Cell cell = this.board.getCell(x + 1, y + 1);

                switch (cell.toString()) {
                    case "·":
                        labelToUpdate.setBackground(Color.LIGHT_GRAY);
                        labelToUpdate.setText("·");
                        break;

                    case "#":
                        labelToUpdate.setBackground(Color.BLUE);
                        break;

                    case "T":
                        labelToUpdate.setBackground(Color.YELLOW);
                        labelToUpdate.setText("T");
                        break;

                    default:
                        if (this.board.isWinner() && this.board.getTreasure() == cell) {
                            labelToUpdate.setBackground(Color.YELLOW);
                        } else {
                            labelToUpdate.setBackground(Color.GRAY);
                        }

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

            if (this.board.getWinner() == h) {
                str.append(" ** WINNER ** ");
            } else {
                str.append("Model.Position actuelle :  ").append(
                        h.getCurrentCell().
                                getPosition().
                                toString()).
                        append(" | Meilleure direction : ").
                        append(h.
                                getDirection().
                                toString());
            }

            str.append(newline);
        }

        this.gameWindow.getLabelStatus().setText(str.toString());
    }
}
