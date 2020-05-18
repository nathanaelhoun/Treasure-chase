import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Treasure chase
 * <p>
 * Controller part of MVC
 *
 * @author Nathanaël Houn
 */
public class Controller implements ActionListener {

    private int boardHeight, boardWidth, numberOfHunters;

    private Window window;
    private Board board;

    public Controller(Window window, int boardHeight, int boardWidth, int numberOfHunters) {
        this.window = window;

        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.numberOfHunters = numberOfHunters;

        this.board = initiateBoard();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == this.window.getButtonNextRound()) {
            this.board.doRound();
            this.updateCellsLabels();
            this.updateStatusLabel();
            return;
        }

        if (ev.getSource() == this.window.getButtonNewGame()) {
            this.board = initiateBoard();
            this.updateCellsLabels();
            this.updateStatusLabel();
            return;
        }

    }

    private Board initiateBoard() {
        return initiateBoard(this.boardHeight, this.boardWidth, this.numberOfHunters);
    }

    private Board initiateBoard(int boardHeight, int boardWidth, int numberOfHunters) {
        Board newBoard = new Board(boardHeight, boardWidth);
        newBoard.initialiseHunters(numberOfHunters);
        newBoard.boardMakePrefabOne();
        return newBoard;
    }

    /**
     * Update all the cells labels from the model
     */
    public void updateCellsLabels() {
        for (int y = 0; y < this.board.getBoardHeight(); y++) {
            for (int x = 0; x < this.board.getBoardWidth(); x++) {

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
                        if (this.board.isWinner() && this.board.getWinner().getCurrentCell() == cell) {
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
        String str = "";

        String newline = System.getProperty("line.separator");
        for (Hunter h : this.board.getHunters()) {
            str += "Personnage " + h.toString() + " : ";

            if (this.board.getWinner() == h) {
                str += " ** WINNER ** ";
            }

            str += h.getDescription() + newline;
        }

        this.window.getLabelStatus().setText(str);
    }
}
