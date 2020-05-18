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

    private Window window;
    private Board board;

    public Controller(Window window, int boardHeight, int boardWidth, int numberOfHunters) {
        this.window = window;
        this.board = new Board(boardHeight, boardWidth);
        board.initialiseHunters(numberOfHunters);
        board.boardMakePrefabOne();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // TODO
    }

    /**
     * Update all the cells labels from the model
     */
    public void updateCellsLabels() {
        for (int y = 0; y < this.board.getBoardHeight(); y++) {
            for (int x = 0; x < this.board.getBoardWidth(); x++) {

                JLabel labelToUpdate = this.window.getCellLabel(x, y);

                String cellType = this.board.getCellString(x + 1, y + 1);

                switch (cellType) {
                    case "·":
                        labelToUpdate.setBackground(Color.LIGHT_GRAY);
                        labelToUpdate.setText(cellType);
                        break;

                    case "#":
                        labelToUpdate.setBackground(Color.BLUE);
                        break;

                    default:
                        labelToUpdate.setBackground(Color.YELLOW);
                        labelToUpdate.setText(cellType);
                }
            }
        }
    }

}
