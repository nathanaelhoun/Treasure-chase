/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 *
 *         Player of the treasure chase
 */
public class Hunter {

    private String name;
    private Direction direction;
    private Position position;

    public Hunter(String name) {
        this.name = name;
        this.direction = Direction.getRandom();
        this.position = new Position(0, 0);
    }

    public String toString() {
        return this.name;
    }

    public boolean setPosition(int x, int y) {
        // TODO : check position is valid
        this.position.setX(x);
        this.position.setY(y);
        return true;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

}
