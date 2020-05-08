/**
 * Treasure chase
 * @author Nathanaël Houn
 *
 */
public abstract class Cell implements Questionable, Comparable<Cell> {

    private final Position position;

    /**
     * @param p Cell position in the map
     */
    public Cell(Position p) {
        this.position = p;
    }

    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    abstract public String toString();

    /**
     * Compute the distance between 2 cells
     * @param that another cell
     * @return int the distance
     */
    public int distanceWith(Cell that) {
        return (int) (Math.pow(this.getPosition().getX() - that.getPosition().getX(), 2) + Math.pow(this.getPosition().getY() - that.getPosition().getY(), 2));
    }

    public int compareTo(Cell that) {
        if (this.position.getY() == that.position.getY()) {
            return this.position.getX() - that.position.getX();
        }

        return this.position.getY() - that.position.getY();
    }
}
