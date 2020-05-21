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
            HashMap<Position, Position> moves = this.board.doRound();
            this.updateCellsLabels(moves);
            this.updateStatusLabel();

            if (this.board.isWinner()) {
                this.window.getButtonNextRound().setEnabled(false);
            }

            return;
        }

        if (ev.getSource() == this.window.getButtonGoToMenu()) {
            this.window.getMenu().setVisible(true);
            this.window.dispose();
        }

//        if (ev.getSource() == this.gameWindow.getButtonNewGame()) {
//            this.board = initiateBoard();
//            this.initialiseCellLabels();
//            this.updateStatusLabel();
//
//            this.gameWindow.getButtonNextRound().setEnabled(true);
//            return;
//        }

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

            JLabel originJLabel = this.window.getCellLabel(originPosition.getX() - 1, originPosition.getY() - 1);
            JLabel destinationJLabel = this.window.getCellLabel(destinationPosition.getX() - 1, destinationPosition.getY() - 1);
            Cell destinationCell = this.board.getCell(destinationPosition.getX(), destinationPosition.getY());

            originJLabel.setBackground(Color.LIGHT_GRAY);
            originJLabel.setText("·");

            if (this.board.isWinner() && this.board.getTreasure() == destinationCell) {
                destinationJLabel.setBackground(Color.YELLOW);
            } else {
                destinationJLabel.setBackground(Color.GRAY);
            }

            destinationJLabel.setText(destinationCell.toString());
        }
    }


    /**
     * Update all the cells labels from the model
     */
    public void initialiseCellLabels() {
        for (int y = 0; y < this.board.HEIGHT; y++) {
            for (int x = 0; x < this.board.HEIGHT; x++) {

                JLabel labelToUpdate = this.window.getCellLabel(x, y);

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

            if (this.board.getTreasure().getWinner() == h) {
                str.append(" ** WINNER ** ");
            } else {
                str.append("Model.Position actuelle :  ").
                        append(h.getCurrentCell().getPosition().toString()).
                        append(" | Meilleure direction : ").
                        append(h.getDirection().toString());
            }
            // TODO : special string when hitting a wall
            str.append(newline);
        }

        this.window.getLabelStatus().setText(str.toString());
    }
}
