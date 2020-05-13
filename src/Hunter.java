/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 * <p>
 * Player of the treasure chase
 */
public class Hunter {

    private String name;
    private Direction direction;
    private CellFree currentCell;

    public Hunter(String name) {
        this.name = name;
        this.direction = Direction.getRandom();
    }

    public String toString() {
        return this.name;
    }

    public void setCurrentCell(CellFree c) {
        this.currentCell = c;
    }

    public CellFree getCurrentCell() {
        return this.currentCell;
    }

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
