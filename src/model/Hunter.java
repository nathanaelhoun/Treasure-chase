package model;

/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 * <p>
 * Player of the treasure chase
 */
public class Hunter {

    /**
     * Name of the Model.Hunter
     */
    private final String name;

    /**
     * Current direction of the Model.Hunter
     */
    private Direction direction;

    /**
     * Instance of the cell where the hunter is
     */
    private Cell currentCell;

    public Hunter(String name) {
        this.name = name;
        this.direction = Direction.getRandom();
    }

    public String toString() {
        return this.name;
    }

    public Cell getCurrentCell() {
        return this.currentCell;
    }

    public void setCurrentCell(Cell c) {
        this.currentCell = c;
    }

    /**
     * Return a string with the position and the direction of the hunter
     *
     * @return String
     */
    public String getDescription() {
        return "Model.Hunter " + this.currentCell.getPosition().toString() + " dir : " + this.direction;
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

        switch(this.direction.toVertical()) {
            case NORTH:
                wantedPosition.setY(wantedPosition.getY() - 1);
                break;
            case SOUTH:
                wantedPosition.setY(wantedPosition.getY() + 1);
                break;
        }

        switch(this.direction.toHorizontal()) {
            case WEST:
                wantedPosition.setX(wantedPosition.getX() - 1);
                break;
            case EAST:
                wantedPosition.setX(wantedPosition.getX() + 1);
                break;
        }

        return wantedPosition;
    }

}
