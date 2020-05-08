/**
 * Treasure chase
 * @author Nathanaël Houn
 *
 */
public class GoTreasureChase {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Board board = new Board(12, 12);

        board.initialiseHunters(3);
        board.initialiseBoard();

        board.printBoard();
    }

}
