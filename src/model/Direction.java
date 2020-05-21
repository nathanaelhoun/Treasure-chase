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
}