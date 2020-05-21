package model;

/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 */
public class CellSide extends Cell {

    private final int boardWidth;
    private final int boardHeight;

    /**
     * @param p           Model.Cell  position in the map
     * @param boardWidth  int         board width
     * @param boardHeight int         board height
     */
    public CellSide(Position p, int boardWidth, int boardHeight) {
        super(p);
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    @Override
    public String toString() {
        return "+";
    }

    /**
     * When a hunter aims to this cell, his direction is reversed and he is not moved
     * <p>
     * If after reversing, the Hunter still aims to a CellSide (e.g. he is in a corner),
     * his position is changed until he doesn't aims to a CellSide
     *
     * @param h the Model.Hunter to process
     */
    @Override
    public void process(Hunter h) {
        h.setDirection(Direction.reverse(h.getDirection()));

        if (isAimingToASide(h)) {
            h.setDirection(Direction.rotate(h.getDirection()));
        }

        if (isAimingToASide(h)) {
            h.setDirection(Direction.reverse(h.getDirection()));
        }
    }

    /**
     * Checks if the given hunter is aiming to a CellSide
     *
     * @param h the Model.Hunter to process
     * @return true if the Hunter is aiming to a CellSide, false otherwise
     */
    private boolean isAimingToASide(Hunter h) {
        return (h.getWantedPosition().getX() == 0 ||
                h.getWantedPosition().getX() == this.boardWidth + 2 ||
                h.getWantedPosition().getY() == 0 ||
                h.getWantedPosition().getY() == this.boardHeight + 2
        );
    }
}
