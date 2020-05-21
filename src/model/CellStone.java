package model;

import java.util.HashMap;

/**
 * Treasure chase
 * <p>
 * A Model.CellStone has eight possible
 * For each one, the best direction is always the same, so they are computed at the initialisation and stored in the map bestDirections
 *
 * @author Nathanaël Houn
 */
public class CellStone extends Cell {

    private final Orientation wallOrientation;
    private final HashMap<Direction, Direction> bestDirections;

    /**
     * @param p Model.Cell position in the map
     */
    public CellStone(Position p, Orientation o) {
        super(p);
        this.wallOrientation = o;
        this.bestDirections = new HashMap<>();
    }

    @Override
    public void setTreasure(CellTreasure c) {
        this.treasure = c;
        this.computeBestDirectionsToTheTreasure();
    }

    @Override
    public String toString() {
        return "#";
    }

    /**
     * When a hunter aims to this cell,
     * - he is not moved
     * - he is redirected to the best direction to the treasure
     * <p>
     * The best direction of the treasure depends on where he is, in relation with the current cell
     * This best direction is computed at the initialisation of the board and found in the map this.bestDirections
     *
     * @param h the Model.Hunter to process
     */
    @Override
    public void process(Hunter h) {
        h.setDirection(this.bestDirections.get(h.getDirection()));
    }

    /**
     * TODO : compute the best direction to the treasure for each position.
     * For the moment, the best direction is always set to Model.Direction.NORTH
     */
    private void computeBestDirectionsToTheTreasure() {
        for (Direction dir : Direction.values()) {
            this.bestDirections.put(dir, Direction.NORTH);
        }
    }


    // ------------------------------------------------------------------------
    //                              Utilities
    // ------------------------------------------------------------------------

    /**
     * The orientation of the whole wall
     */
    public enum Orientation {
        VERTICAL, HORIZONTAL
    }
}