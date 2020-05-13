/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 */
public class GoTreasureChase {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Board board = new Board(12, 12);

        board.initialiseHunters(3);
        board.initialiseBoard();

        int toDoDevTemp = 0;
        do {
            board.processHunters();
            board.printBoard();
            ++toDoDevTemp;
        } while (!board.isWinner() && toDoDevTemp < 200);

        board.printBoard();

        System.out.format("Nombre de tours : %d\nGagnant : %s\n", toDoDevTemp, board.getWinner());
    }

}
