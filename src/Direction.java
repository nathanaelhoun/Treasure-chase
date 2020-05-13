/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 * <p>
 * The 8 possible directions of a Hunter in a Cell
 */
public enum Direction {
    EAST, NORTH_EAST, NORTH, NORTH_WEST, WEST, SOUTH_WEST, SOUTH, SOUTH_EAST;


    private static Direction[] vals = values();

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

        return this.toString();
    }

    /**
     * Return the opposite direction (for example, the opposite of TOP_RIGHT is BOTTOM_LEFT
     *
     * @param dir the current direction
     * @return the opposite direction
     */
    public static Direction reverse(Direction dir) {
        return vals[(dir.ordinal() + vals.length / 2) % vals.length];
    }

    /**
     * @return a random direction.
     */
    public static Direction getRandom() {
        return vals[(int) Math.random() * (vals.length)];
    }
}