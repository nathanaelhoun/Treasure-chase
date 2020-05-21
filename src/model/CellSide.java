package model;

/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 */
public class CellSide extends Cell {

    /**
     * @param p Model.Cell position in the map
     */
    public CellSide(Position p) {
        super(p);
    }

    @Override
    public String toString() {
        return "+";
    }

    /**
     * When a hunter aims to this cell, his direction is reversed and he is not moved
     *
     * @param h the Model.Hunter to process
     */
    @Override
    public void process(Hunter h) {
        h.setDirection(Direction.reverse(h.getDirection()));
    }
}
