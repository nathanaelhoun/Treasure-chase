/**
 * Treasure chase
 * @author Nathanaël Houn
 *
 */
public class CellTreasure extends Cell {

    /**
     * @param p Cell position in the map
     */
    public CellTreasure(Position p) {
        super(p);
    }

    public void process(Hunter h) {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return "T";
    }

}
