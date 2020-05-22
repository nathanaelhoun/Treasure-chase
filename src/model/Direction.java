package model;

/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 * <p>
 * The 8 possible directions of a Model.Hunter in a Model.Cell
 */
public enum Direction {

    /**
     * Model.Position right, number 1
     */
    EAST,
    /**
     * Model.Position top right, number 2
     */
    NORTH_EAST,
    /**
     * Model.Position top, number 3
     */
    NORTH,
    /**
     * Model.Position top left, number 4
     */
    NORTH_WEST,
    /**
     * Model.Position left, number 5
     */
    WEST,
    /**
     * Model.Position bottom left, number 6
     */
    SOUTH_WEST,
    /**
     * Model.Position left, number 7
     */
    SOUTH,
    /**
     * Model.Position bottom right, number 8
     */
    SOUTH_EAST;

    private static final Direction[] values = values();

    /**
     * Return the opposite direction (for example, the opposite of NORTH_EAST is SOUTH_WEST)
     *
     * @param dir the current direction
     * @return the opposite direction
     */
    public static Direction reverse(Direction dir) {
        return values[(dir.ordinal() + values.length / 2) % values.length];
    }

    /**
     * Return the anti-clockwise rotated direction
     *
     * @param dir the current direction
     * @return the rotated direction
     */
    public static Direction rotate(Direction dir) {
        return values[(dir.ordinal() + values.length / 4) % values.length];
    }

    /**
     * @return a random direction
     */
    public static Direction getRandom() {
        return values[(int) (Math.random() * (values.length))];
    }

    /**
     * Get the four cardinal points
     * @return an array of four directions
     */
    public static Direction[] getCardinalPoints() {
        return new Direction[]{NORTH, EAST, SOUTH, WEST};
    }

    @Override
    public String toString() {
        switch (this) {
            case EAST:
                return "Droite";

            case NORTH_EAST:
                return "En haut à droite";

            case NORTH:
                return "En haut";

            case NORTH_WEST:
                return "En haut à gauche";

            case WEST:
                return "Gauche";

            case SOUTH_WEST:
                return "En bas à gauche";

            case SOUTH:
                return "En bas";

            case SOUTH_EAST:
                return "En bas à droite";
        }

        return super.toString();
    }

    public String toBeautifulString() {
        switch (this) {
            case EAST:
                return "→";

            case NORTH_EAST:
                return "↗";

            case NORTH:
                return "↑";

            case NORTH_WEST:
                return "↖";

            case WEST:
                return "←";

            case SOUTH_WEST:
                return "↙";

            case SOUTH:
                return "↓";

            case SOUTH_EAST:
                return "↘";
        }
        return super.toString();
    }

    /**
     * Convert a direction to the nearest vertical direction (for example, NORTH_EAST -> NORTH)
     *
     * @return a vertical direction
     */
    public Direction toVertical() {
        switch (this) {
            case NORTH_EAST:
            case NORTH:
            case NORTH_WEST:
                return Direction.NORTH;

            case SOUTH_WEST:
            case SOUTH:
            case SOUTH_EAST:
                return Direction.SOUTH;

            default:
                return this;
        }
    }

    /**
     * Convert a direction to the nearest horizontal direction (for example, NORTH_EAST -> EAST)
     *
     * @return a horizontal direction
     */
    public Direction toHorizontal() {
        switch (this) {
            case NORTH_EAST:
            case SOUTH_EAST:
            case EAST:
                return Direction.EAST;

            case NORTH_WEST:
            case SOUTH_WEST:
            case WEST:
                return Direction.WEST;

            default:
                return this;
        }
    }

}