package model;

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
        this.directionToTheTreasure = null;
    }

    public void setCurrentHunter(Hunter h) {
        assert (this.currentHunter == null);
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

        return currentHunter.toString() + currentHunter.getDirection().toBeautifulString();
    }

    /**
     * When a hunter aims to this cell, he is :
     * - redirected to a random direction if the cell is taken by another hunter
     * - moved to the current cell and redirected to the best direction to the treasure if the cell is free
     * <p>
     * When the first Hunter ask to be processed, calculate the best direction to the treasure
     * and store it in directionToTheTreasure for the next Hunter
     *
     * @param h the Model.Hunter to process
     */
    @Override
    public void process(Hunter h) {
        if (this.currentHunter != null) {
            h.setDirection(Direction.getRandom());
            return;
        }

        ((CellFree) h.getCurrentCell()).removeHunter();
        this.currentHunter = h;
        h.setCurrentCell(this);

        if (this.directionToTheTreasure == null) {
            this.computeDirectionToTheTreasure();
        }
        h.setDirection(this.directionToTheTreasure);
    }

    // ------------------------------------------------------------------------
    //                              Utilities
    // ------------------------------------------------------------------------

    /**
     * Search the direction to the treasure from the current cell
     * and save it in the cell
     */
    private void computeDirectionToTheTreasure() {
        Direction computedDirection = Direction.SOUTH_WEST; // initialised with a random useless value
        int lowerDistance = Integer.MAX_VALUE;

        for (Direction dir : Direction.values()) {
            Position testPosition = this.position.computePosition(dir);
            int testedDistance = this.treasure.distanceWith(testPosition);

            if (testedDistance < lowerDistance) {
                lowerDistance = testedDistance;
                computedDirection = dir;
            }
        }

        this.directionToTheTreasure = computedDirection;
    }
}

