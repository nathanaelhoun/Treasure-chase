/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 */
public abstract class Cell implements Questionable, Comparable<Cell> {

    protected final Position position;
    protected CellTreasure treasure;
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
        return this.position;
    }

    public void setTreasure(CellTreasure c) {
        this.treasure = c;
    }

    public CellTreasure getTreasure() {
        return this.treasure;
    }

    abstract public String toString();

    /**
     * Compute the distance between the cell and another one
     *
     * @param that another cell
     * @return int the distance
     */
    public int distanceWith(Cell that) {
        return distanceWith(that.getPosition());
    }

    /**
     * Compute the distance between the cell and another one
     *
     * @param pos position of the other cell
     * @return int the distance
     */
    public int distanceWith(Position pos) {
        return (int) (Math.pow(this.getPosition().getX() - pos.getX(), 2) + Math.pow(this.getPosition().getY() - pos.getY(), 2));
    }


    public int compareTo(Cell that) {
        if (this.position.getY() == that.position.getY()) {
            return this.position.getX() - that.position.getX();
        }

        return this.position.getY() - that.position.getY();
    }
}
