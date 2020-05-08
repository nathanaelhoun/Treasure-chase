/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 *
 */
public class CellStone extends Cell {

    public enum Orientation {
        VERTICAL, HORIZONTAL;
    }

    private final Orientation orientation;

    /**
     * @param p Cell position in the map
     */
    public CellStone(Position p, Orientation o) {
        super(p);
        this.orientation = o;
    }

    /**
     * @return the orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    public void process(Hunter h) {
        // TODO Auto-generated method stub

    }

    public String toString() {
        return "#";
    }
}
