/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 */
public abstract class Cell implements Questionable {

    /**
     * Position of the cell on the board
     */
    protected final Position position;

    /**
     * Instance of the treasure
     */
    protected CellTreasure treasure;

    /**
     * @param p Cell position in the map
     */
    public Cell(Position p) {
        this.position = p;
    }

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
     * Compute the distance between the cell and a given another one
     *
     * @param that another cell
     * @return int the distance
     */
    public int distanceWith(Cell that) {
        return distanceWith(that.getPosition());
    }

    /**
     * Compute the distance between the cell and a given position
     *
     * @param pos position to compare
     * @return int the distance
     */
    public int distanceWith(Position pos) {
        return (int) (Math.pow(this.getPosition().getX() - pos.getX(), 2) + Math.pow(this.getPosition().getY() - pos.getY(), 2));
    }
}
