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
        board.boardMakePrefabOne();

        // Variable to stop in case of infinite loop
        int toDoDevTemp = 0;

        do {
            board.doRound();
            System.out.println(board.toString());
            ++toDoDevTemp;
        } while (!board.isWinner() && toDoDevTemp < 200);

        System.out.println(board.toString());

        System.out.format("Nombre de tours : %d\nGagnant : %s\n", toDoDevTemp, board.getWinner());
    }

}
