import java.util.HashMap;

/**
 * Treasure chase
 *
 * A CellStone has eight possible
 * For each one, the best direction is always the same, so they are computed at the initialisation and stored in the map bestDirections
 * @author Nathanaël Houn
 */
public class CellStone extends Cell {

    public enum Orientation {
        VERTICAL, HORIZONTAL;
    }

    private final Orientation wallOrientation;
    private HashMap<Direction, Direction> bestDirections;

    /**
     * @param p Cell position in the map
     */
    public CellStone(Position p, Orientation o) {
        super(p);
        this.wallOrientation = o;
        this.bestDirections = new HashMap<>();
    }

    /**
     * @return the orientation
     */
    public Orientation getOrientation() {
        return wallOrientation;
    }

    @Override
    public void setTreasure(CellTreasure c) {
        this.treasure = c;
        this.computeBestDirectionsToTheTreasure();
    }

    public void process(Hunter h) {
        h.setDirection(this.bestDirections.get(h.getDirection()));
    }

    public String toString() {
        return "#";
    }

    /**************************************************************************
     * 							Utilities
     *************************************************************************/
    private void computeBestDirectionsToTheTreasure() {
        for (Direction dir : Direction.values()) {
            this.bestDirections.put(dir, Direction.NORTH);
        }
    }
}
