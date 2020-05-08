/**
 * Treasure chase
 * @author Nathanaël Houn
 *
 * The 8 possible directions of a Hunter in a Cell
 */
public enum Direction {
    RIGHT, TOP_RIGHT, TOP, TOP_LEFT, LEFT, BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT;
    
    /**
     * @return a random direction.
     */
    public static Direction getRandom() {
        return values()[(int) Math.random() * (values().length)];
    }
}