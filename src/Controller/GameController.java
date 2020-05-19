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

    public GameController(GameWindow gameWindow, int boardNumber) {
        this.gameWindow = gameWindow;

        this.board = new Board(boardNumber);
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
            return;
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
                            // Deactivate the buttons
                            this.gameWindow.getButtonNextRound().setEnabled(false);
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
        String str = "";

        String newline = System.getProperty("line.separator");
        for (Hunter h : this.board.getHunters()) {
            str += "Personnage " + h.toString() + " : ";

            if (this.board.getWinner() == h) {
                str += " ** WINNER ** ";
            } else {
                str += "Model.Position actuelle :  " + h.getCurrentCell().getPosition().toString() + " | Meilleure direction : " + h.getDirection().toString();
            }

            str += newline;
        }

        this.gameWindow.getLabelStatus().setText(str);
    }
}
