package Model;

/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 */
public class CellFree extends Cell {

    private Hunter currentHunter;
    private Direction directionToTheTreasure;

    /**
     * @param p Model.Cell position in the map
     */
    public CellFree(Position p) {
        super(p);
        this.currentHunter = null;
    }

    @Override
    public void setTreasure(CellTreasure c) {
        this.treasure = c;
        this.directionToTheTreasure = this.computeDirectionToTheTreasure();
    }

    public void setHunter(Hunter h) {
        if (this.currentHunter != null) {
            System.err.println("Il y a déjà un Model.Hunter dans cette case");
            return;
        }

        this.currentHunter = h;
    }

    public void removeHunter() {
        this.currentHunter = null;
    }

    @Override
    public String toString() {
        if (this.currentHunter == null) {
            return "·";
        }

        return currentHunter.toString();
    }

    /**
     * When a hunter aims to this cell, he is :
     * - redirected to a random direction if the cell is taken by another hunter
     * - moved to the current cell and redirected to the best direction to the treasure if the cell is free
     *
     * @param h the Model.Hunter to process
     */
    @Override
    public void process(Hunter h) {
        if (this.currentHunter != null) {
            h.setDirection(Direction.getRandom());
            return;
        }

        h.getCurrentCell().removeHunter();
        this.currentHunter = h;
        h.setCurrentCell(this);
        h.setDirection(this.directionToTheTreasure);
    }

    /**************************************************************************
     * 							Utilities
     *************************************************************************/

    /**
     * Search the direction to the treasure from the current cell
     *
     * @return the direction to the treasure
     */
    private Direction computeDirectionToTheTreasure() {
        Direction directionToTheTreasure = Direction.SOUTH_WEST; // initialised with a random useless value

        int lowerDistance = Integer.MAX_VALUE;

        for (Direction dir : Direction.values()) {
            Position testPosition = this.position.currentPositionPlusDirection(dir);
            int testedDistance = this.treasure.distanceWith(testPosition);
            if (testedDistance < lowerDistance) {
                lowerDistance = testedDistance;
                directionToTheTreasure = dir;
            }
        }

        return directionToTheTreasure;
    }
}

