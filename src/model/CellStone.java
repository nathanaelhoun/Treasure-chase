package model;

/**
 * Treasure chase
 * <p>
 * A Model.CellStone has eight possible
 * For each one, the best direction is always the same, so they are computed at the initialisation and stored in the map bestDirections
 *
 * @author Nathanaël Houn
 */
public class CellStone extends Cell {

    /**
     * The orientation of the whole wall
     */
    public enum Orientation {
        VERTICAL, HORIZONTAL
    }

    private final Orientation wallOrientation;
    private final Board board;

    /**
     * @param p Model.Cell position in the map
     */
    public CellStone(Position p, Orientation o, Board board) {
        super(p);
        this.wallOrientation = o;
        this.board = board;
    }

    @Override
    public void setTreasure(CellTreasure c) {
        this.treasure = c;
    }

    @Override
    public String toString() {
        return "#";
    }

    /**
     * When a hunter aims to this cell,
     * - he is not moved
     * - he is redirected to the best direction to the treasure
     * <p>
     * The best direction of the treasure depends on where it is, in relation with the current location of the hunter
     * We found it by computing the distance to each end of the wall, and then the distance with the treasure from the end of the wall
     * The lower value between each side is the best direction to follow
     *
     * @param h the Model.Hunter to process
     */
    @Override
    public void process(Hunter h) {
        Direction newDirection;
        switch (this.wallOrientation) {
            case HORIZONTAL:
                Cell wallWest = findWestEndOfTheWall();
                Cell wallEast = findEastEndOfTheWall();

                int distanceByWest = wallWest.distanceWith(h.getCurrentCell())
                        + wallWest.distanceWith(this.treasure);
                int distanceByEast = wallEast.distanceWith(h.getCurrentCell())
                        + wallEast.distanceWith(this.treasure);

                newDirection = Direction.WEST;
                if (distanceByWest > distanceByEast) {
                    newDirection = Direction.EAST;
                }
                break;

            case VERTICAL:
                Cell wallNorth = findNorthEndOfTheWall();
                Cell wallSouth = findSouthEndOfTheWall();

                int distanceBySouth = wallSouth.distanceWith(h.getCurrentCell())
                        + wallSouth.distanceWith(this.treasure);
                int distanceByNorth = wallNorth.distanceWith(h.getCurrentCell())
                        + wallNorth.distanceWith(this.treasure);

                newDirection = Direction.NORTH;
                if (distanceByNorth > distanceBySouth) {
                    newDirection = Direction.SOUTH;
                }
                break;

            default:
                throw new IllegalStateException("Valeur impossible : " + this.wallOrientation);
        }

        h.setDirection(newDirection);
    }


    // ------------------------------------------------------------------------
    //                              Utilities
    // ------------------------------------------------------------------------

    /**
     * @return the cell at the north end of the wall
     */
    private Cell findNorthEndOfTheWall() {
        assert (this.wallOrientation == Orientation.VERTICAL);

        int topY = this.position.getY();
        while (this.board.getCell(this.position.getX(), topY - 1).toString().equals(this.toString())) {
            --topY;
        }

        return this.board.getCell(this.position.getX(), topY);
    }

    /**
     * @return the cell at the south end of the wall
     */
    private Cell findSouthEndOfTheWall() {
        assert (this.wallOrientation == Orientation.VERTICAL);

        int bottomY = this.position.getY();
        while (this.board.getCell(this.position.getX(), bottomY + 1).toString().equals(this.toString())) {
            ++bottomY;
        }

        return this.board.getCell(this.position.getX(), bottomY);
    }

    /**
     * @return the cell at the west end of the wall
     */
    private Cell findWestEndOfTheWall() {
        assert (this.wallOrientation == Orientation.HORIZONTAL);

        int leftX = this.position.getX();
        while (this.board.getCell(leftX - 1, this.position.getY()).toString().equals(this.toString())) {
            --leftX;
        }

        return this.board.getCell(leftX, this.position.getY());
    }

    /**
     * @return the cell at the east end of the wall
     */
    private Cell findEastEndOfTheWall() {
        assert (this.wallOrientation == Orientation.HORIZONTAL);

        int rightX = this.position.getX();
        while (this.board.getCell(rightX + 1, this.position.getY()).toString().equals(this.toString())) {
            ++rightX;
        }

        return this.board.getCell(rightX, this.position.getY());
    }
}
