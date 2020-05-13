/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 *
 */
public class CellSide extends Cell {

    /**
     * @param p Cell position in the map
     */
    public CellSide(Position p) {
        super(p);
    }

    @Override
    public void process(Hunter h) {
        h.setDirection(Direction.reverse(h.getDirection()));
    }

    @Override
    public String toString() {
        return "+";
    }

}
