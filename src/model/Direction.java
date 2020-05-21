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
     * Model.Position à droite, numéro 1
     */
    EAST,
    /**
     * Model.Position en haut à droite, numéro 2
     */
    NORTH_EAST,
    /**
     * Model.Position en haut, numéro 3
     */
    NORTH,
    /**
     * Model.Position en haut à gauche, numéro 4
     */
    NORTH_WEST,
    /**
     * Model.Position à gauche, numéro 5
     */
    WEST,
    /**
     * Model.Position en bas à gauche, numéro 6
     */
    SOUTH_WEST,
    /**
     * Model.Position à gauche, numéro 7
     */
    SOUTH,
    /**
     * Model.Position en bas à droite, numéro 8
     */
    SOUTH_EAST;


    private static final Direction[] vals = values();

    /**
     * Return the opposite direction (for example, the opposite of NORTH_EAST is SOUTH_WEST
     *
     * @param dir the current direction
     * @return the opposite direction
     */
    public static Direction reverse(Direction dir) {
        return vals[(dir.ordinal() + vals.length / 2) % vals.length];
    }

    /**
     * @return a random direction
     */
    public static Direction getRandom() {
        return vals[(int) (Math.random() * (vals.length))];
    }

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
}