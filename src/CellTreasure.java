/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 */
public class CellTreasure extends Cell {

    private Hunter winner;

    /**
     * @param p Cell position in the map
     */
    public CellTreasure(Position p) {
        super(p);
        this.winner = null;
    }

    /**
     * If a hunter aims to this cell, so he is the winner !
     *
     * @param h the Hunter to process
     */
    @Override
    public void process(Hunter h) {
        this.winner = h;
    }

    @Override
    public String toString() {
        return "T";
    }

    public Hunter getWinner() {
        return this.winner;
    }
}
