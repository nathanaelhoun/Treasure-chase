package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 */
public class Board {
    public final int HEIGHT;
    public final int WIDTH;
    private final ArrayList<ArrayList<Cell>> cells;
    private final ArrayList<Hunter> hunters;
    private CellTreasure treasure;

    /**
     * Initialize an empty (filled with CellFree) board
     *
     * @param width  the width of the board, without the sides
     * @param height the height of the board, without the sides
     */
    public Board(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.cells = new ArrayList<>();
        for (int i = 0; i < this.HEIGHT + 2; ++i) {
            this.cells.add(new ArrayList<>());
        }
        this.boardMakeEmpty();

        this.hunters = new ArrayList<>();
        this.treasure = null;
    }

    /**
     * Initialize a saved board
     *
     * @param boardNumber the board number
     */
    public Board(int boardNumber) {
        this.cells = new ArrayList<>();
        this.hunters = new ArrayList<>();
        this.treasure = null;

        switch (boardNumber) {
            case 1:
            default:
                // Make this board
                // ++++++++++++++
                // +············+
                // +············+
                // +···#········+
                // +·T·#·····A··+
                // +···#········+
                // +···#········+
                // +···#··###···+
                // +···#···C····+
                // +···#····B···+
                // +············+
                // +············+
                // +············+
                // ++++++++++++++
                this.HEIGHT = 12;
                this.WIDTH = 12;
                for (int i = 0; i < this.HEIGHT + 2; ++i) {
                    this.cells.add(new ArrayList<>());
                }
                initialiseTopLeftAndBottomSides();
                initialiseEmptyLine(1);
                initialiseEmptyLine(2);
                initialiseLine(3, new String[]{".", ".", ".", "#V", ".", ".", ".", ".", ".", ".", ".", "."});
                initialiseLine(4, new String[]{".", "T", ".", "#V", ".", ".", ".", ".", ".", "A", ".", "."});
                initialiseLine(5, new String[]{".", ".", ".", "#V", ".", ".", ".", ".", ".", ".", ".", "."});
                initialiseLine(6, new String[]{".", ".", ".", "#V", ".", ".", ".", ".", ".", ".", ".", "."});
                initialiseLine(7, new String[]{".", ".", ".", "#V", ".", ".", "#H", "#H", "#H", ".", ".", "."});
                initialiseLine(8, new String[]{".", ".", ".", "#V", ".", ".", ".", "C", ".", ".", ".", "."});
                initialiseLine(9, new String[]{".", ".", ".", "#V", ".", ".", ".", ".", "B", ".", ".", "."});
                initialiseEmptyLine(10);
                initialiseEmptyLine(11);
                initialiseEmptyLine(12);
                initialiseRightSide();
        }
        this.treasure = this.getTreasure();
        this.setTreasureForAllCells();
    }

    /**
     * Get the treasure
     * <p>
     * If the treasure attribute is not set, attempt to find it on the board before giving it.
     *
     * @return the instance of the treasure from the board
     */
    public CellTreasure getTreasure() {
        if (this.treasure != null) {
            return this.treasure;
        }

        this.treasure = this.findTreasure();
        return this.treasure;
    }

    /**
     * Find the treasure on the map and set it
     *
     * @return true if there is a treasure, false is none is found
     */
    public boolean findAndSetTreasure() {
        CellTreasure treasure = findTreasure();
        if (treasure == null) {
            return false;
        }

        this.treasure = treasure;
        this.setTreasureForAllCells();
        return true;
    }

    public ArrayList<Hunter> getHunters() {
        return hunters;
    }

    public Cell getCell(int x, int y) {
        return this.cells.get(y).get(x);
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (ArrayList<Cell> line : cells) {
            for (Cell cell : line) {
                boardString.append(cell.toString());
            }
            boardString.append('\n');
        }

        return boardString.toString();
    }

    /**
     * Do a round of the game
     * <p>
     * Move all the hunters one after the other
     * Update their position if needed
     * Output the changes done to the console
     */
    public HashMap<Position, Position> doRound() {
        HashMap<Position, Position> moves = new HashMap<>();
        for (Hunter h : this.hunters) {
            Position originPosition = h.getCurrentCell().getPosition();

            System.out.println("Personnage " + h.toString() + ": ");
            System.out.println(h.getDescription());

            Position wantedPos = h.getWantedPosition();
            System.out.println("Case cible : " + wantedPos.toString());

            this.cells.get(wantedPos.getY()).get(wantedPos.getX()).process(h);
            System.out.println("Best dir : " + h.getDirection());

            System.out.println(" -> " + h.getDescription() + "\n");

            Position destinationPosition = h.getCurrentCell().getPosition();

            moves.put(originPosition, destinationPosition);
        }

        return moves;
    }

    /**
     * @return true if a hunter is aiming to the Treasure cell
     */
    public boolean isWinner() {
        return this.treasure.getWinner() != null;
    }

    // ------------------------------------------------------------------------
    //                               Utilities
    // ------------------------------------------------------------------------

    /**
     * Find the treasure on the board
     *
     * @return the treasure from the board, or null if none is found
     */
    private CellTreasure findTreasure() {
        for (ArrayList<Cell> line : cells) {
            for (Cell cell : line) {
                if (cell.toString().equals("T")) {
                    return (CellTreasure) cell;
                }
            }
        }

        return null;
    }

    /**
     * Place CellSide on top, left and bottom sides of the board
     */
    private void initialiseTopLeftAndBottomSides() {
        for (int x = 0; x < this.WIDTH + 2; ++x) {
            this.cells.get(0).add(new CellSide(new Position(x, 0), this.WIDTH, this.HEIGHT));
        }

        for (int y = 1; y < this.HEIGHT + 1; ++y) {
            this.cells.get(y).add(new CellSide(new Position(0, y), this.WIDTH, this.HEIGHT));
        }

        for (int x = 0; x < this.WIDTH + 2; ++x) {
            this.cells.get(this.HEIGHT + 1).add(new CellSide(new Position(x, this.HEIGHT + 2), this.WIDTH, this.HEIGHT));
        }
    }

    /**
     * Add CellSide at the end of each line, to do the right side of the board
     */
    private void initialiseRightSide() {
        for (int y = 1; y < this.HEIGHT + 1; ++y) {
            this.cells.get(y).add(new CellSide(new Position(this.WIDTH + 2, y), this.WIDTH, this.HEIGHT));
        }
    }

    /**
     * Initialize an line of free CelLFree on the board
     *
     * @param lineY the number of the line to set
     */
    private void initialiseEmptyLine(int lineY) {
        for (int x = 1; x <= this.WIDTH; ++x) {
            this.cells.get(lineY).add(new CellFree(new Position(x, lineY)));
        }
    }

    /**
     * Initialize a line on the board according to the given description array of strings
     *
     * @param lineY the number of the line to set
     * @param toSet an array of strings, with #V and #H for walls, "T" for treasure, "." for an empty CellFree and a lettre for a hunter
     */
    private void initialiseLine(int lineY, String[] toSet) {
        if (toSet.length != this.WIDTH) {
            System.err.println("Nombre d'arguments non valide : doit faire la largeur du plateau.");
        }

        for (int x = 1; x <= this.WIDTH; ++x) {
            Position pos = new Position(x, lineY);
            Cell newCell;

            switch (toSet[x - 1]) {
                case "#V":
                    newCell = new CellStone(pos, CellStone.Orientation.VERTICAL, this);
                    break;

                case "#H":
                    newCell = new CellStone(pos, CellStone.Orientation.HORIZONTAL, this);
                    break;

                case "T":
                    newCell = new CellTreasure(pos);
                    break;

                case ".":
                    newCell = new CellFree(pos);
                    break;

                default:
                    // Add the hunter on the map
                    newCell = new CellFree(pos);

                    Hunter h = new Hunter(toSet[x - 1]);
                    this.hunters.add(h);
                    ((CellFree) newCell).setCurrentHunter(h);
                    h.setCurrentCell(newCell);
            }

            this.cells.get(lineY).add(newCell);
        }
    }

    /**
     * Set the treasure location for each cell.
     * For some cells, this make compute the best direction
     */
    private void setTreasureForAllCells() {
        for (ArrayList<Cell> line : cells) {
            for (Cell c : line) {
                c.setTreasure(this.treasure);
            }
        }
    }

    // ------------------------------------------------------------------------
    //                                  For the Editor
    // ------------------------------------------------------------------------

    /**
     * Initialize an empty board with empty CellFree
     */
    private void boardMakeEmpty() {
        initialiseTopLeftAndBottomSides();

        for (int y = 1; y <= this.HEIGHT; y++) {
            initialiseEmptyLine(y);
        }

        initialiseRightSide();
    }

    /**
     * Replace a selected cell in the board by the given one
     *
     * @param x    abscissa of the cell to replace
     * @param y    ordinate of the cell to replace
     * @param cell the new cell
     */
    public void replaceCell(int x, int y, Cell cell) {
        this.cells.get(y).set(x, cell);
    }

    /**
     * Add a hunter to the board
     *
     * @param name the name of the new Hunter. If the name is empty, generate a new name
     * @return the new Hunter
     */
    public Hunter addHunter(String name) {
        if (name.length() == 0) {
            name = Character.toString((char) (this.hunters.size() + 'A'));
        }

        Hunter newHunter = new Hunter(name);
        this.hunters.add(newHunter);
        return newHunter;
    }

    /**
     * Remove previously saved informations
     */
    public void cleanup() {
        this.hunters.clear();
        this.treasure = null;
    }
}
