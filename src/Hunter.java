/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 * <p>
 * Player of the treasure chase
 */
public class Hunter {

    /**
     * Name of the Hunter
     */
    private String name;

    /**
     * Current direction of the Hunter
     */
    private Direction direction;

    /**
     * Instance of the cell where the hunter is
     */
    private CellFree currentCell;

    public Hunter(String name) {
        this.name = name;
        this.direction = Direction.getRandom();
    }

    public String toString() {
        return this.name;
    }

    public CellFree getCurrentCell() {
        return this.currentCell;
    }

    public void setCurrentCell(CellFree c) {
        this.currentCell = c;
    }

    /**
     * Return a string with the position and the direction of the hunter
     *
     * @return String
     */
    public String getDescription() {
        return "Hunter " + this.currentCell.getPosition().toString() + " dir : " + this.direction;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction d) {
        this.direction = d;
    }

    /**
     * Compute the wanted position, by adding the direction to the current position of the hunter
     *
     * @return a new instance of the wanted position
     */
    public Position getWantedPosition() {
        Position wantedPosition = new Position(this.currentCell.getPosition().getX(), this.currentCell.getPosition().getY());

        if (this.direction == Direction.NORTH || this.direction == Direction.NORTH_EAST || this.direction == Direction.NORTH_WEST) {
            wantedPosition.setY(wantedPosition.getY() - 1);
        }

        if (this.direction == Direction.SOUTH || this.direction == Direction.SOUTH_EAST || this.direction == Direction.SOUTH_WEST) {
            wantedPosition.setY(wantedPosition.getY() + 1);
        }

        if (this.direction == Direction.WEST || this.direction == Direction.NORTH_WEST || this.direction == Direction.SOUTH_WEST) {
            wantedPosition.setX(wantedPosition.getX() - 1);
        }

        if (this.direction == Direction.EAST || this.direction == Direction.NORTH_EAST || this.direction == Direction.SOUTH_EAST) {
            wantedPosition.setX(wantedPosition.getX() + 1);
        }

        return wantedPosition;
    }

}
