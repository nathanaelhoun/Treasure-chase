package model;

/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 * <p>
 * The position of a player in the grid
 */
public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return "[" + this.x + " " + this.y + "]";
    }


    /**
     * Compute the position obtained with the current position + the given direction
     *
     * @param dir the direction to follow
     * @return the next position
     */
    public Position currentPositionPlusDirection(Direction dir) {
        Position computedPosition = new Position(this.getX(), this.getY());

        if (dir == Direction.NORTH || dir == Direction.NORTH_EAST || dir == Direction.NORTH_WEST) {
            computedPosition.setY(computedPosition.getY() - 1);
        }

        if (dir == Direction.SOUTH || dir == Direction.SOUTH_EAST || dir == Direction.SOUTH_WEST) {
            computedPosition.setY(computedPosition.getY() + 1);
        }

        if (dir == Direction.WEST || dir == Direction.NORTH_WEST || dir == Direction.SOUTH_WEST) {
            computedPosition.setX(computedPosition.getX() - 1);
        }

        if (dir == Direction.EAST || dir == Direction.NORTH_EAST || dir == Direction.SOUTH_EAST) {
            computedPosition.setX(computedPosition.getX() + 1);
        }

        return computedPosition;
    }
}
