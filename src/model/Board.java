package model;

import java.util.ArrayList;

/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 */
public class Board {
    private final int boardHeight;
    private final int boardWidth;
    private final ArrayList<ArrayList<Cell>> cells;
    private final ArrayList<Hunter> hunters;
    private CellTreasure treasure;

    public Board(int width, int height) {
        this.boardWidth = width;
        this.boardHeight = height;
        this.cells = new ArrayList<>();
        for (int i = 0; i < this.boardHeight + 2; ++i) {
            this.cells.add(new ArrayList<>());
        }
        this.boardMakeEmpty();

        this.hunters = new ArrayList<>();
        this.treasure = null;
    }

    public Board(int boardNumber) {
        this.cells = new ArrayList<>();
        this.hunters = new ArrayList<>();
        this.treasure = null;

        switch (boardNumber) {
            case 1:
            default:
                this.boardHeight = 12;
                this.boardWidth = 12;
                for (int i = 0; i < this.boardHeight + 2; ++i) {
                    this.cells.add(new ArrayList<>());
                }
                initialiseHunters(3);
                this.boardMakePrefabOne();
        }
    }

    public int getBoardHeight() {
        return this.boardHeight;
    }

    public int getBoardWidth() {
        return this.boardWidth;
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
     * Set the treasure for the object and for all the cells
     *
     * @param treasure the new CellTreasure instance
     */
    public void setTreasure(CellTreasure treasure) {
        this.treasure = treasure;
        this.setTreasureForAllCells();
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

        this.setTreasure(treasure);
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
        StringBuilder board = new StringBuilder();
        for (ArrayList<Cell> line : cells) {
            for (Cell cell : line) {
                board.append(cell.toString());
            }
            board.append('\n');
        }

        return board.toString();
    }

    /**
     * Place the cells on the board like this
     * <p>
     * ++++++++++++++
     * +············+
     * +············+
     * +···#········+
     * +·T·#·····A··+
     * +···#········+
     * +···#········+
     * +···#··###···+
     * +···#···C····+
     * +···#····B···+
     * +············+
     * +············+
     * +············+
     * ++++++++++++++
     */
    public void boardMakePrefabOne() {
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

        this.treasure = this.getTreasure();
        this.setTreasureForAllCells();
    }

    /**
     * Initialise the hunter array but don't place the hunters on the board
     *
     * @param numberOfHunters the number of hunter to generate
     */
    private void initialiseHunters(int numberOfHunters) {
        for (int i = 0; i < numberOfHunters; i++) {
            String name = "" + (char) (i + 'A');
            this.hunters.add(new Hunter(name));
        }
    }

    /**
     * Do a round of the game
     * <p>
     * Move all the hunters one after the other
     * Update their position if needed
     * Output the changes done to the console
     */
    public void doRound() {
        for (Hunter h : this.hunters) {
            System.out.println("Personnage " + h.toString() + ": ");
            System.out.println(h.getDescription());

            Position wantedPos = h.getWantedPosition();
            System.out.println("Case cible : " + wantedPos.toString());

            this.cells.get(wantedPos.getY()).get(wantedPos.getX()).process(h);
            System.out.println("Best dir : " + h.getDirection());

            System.out.println(" -> " + h.getDescription() + "\n");
        }
    }

    /**
     * @return true if a hunter is aiming to the Treasure cell
     */
    public boolean isWinner() {
        return this.treasure.getWinner() != null;
    }

    /**
     * Get the winner of the game
     * <p>
     * Please call isWinner() before
     *
     * @return the instance of the winner Model.Hunter
     */
    public Hunter getWinner() {
        return this.treasure.getWinner();
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
        for (int x = 0; x < this.boardWidth + 2; ++x) {
            this.cells.get(0).add(new CellSide(new Position(x, 0), this.boardWidth, this.boardHeight));
        }

        for (int y = 1; y < this.boardHeight + 1; ++y) {
            this.cells.get(y).add(new CellSide(new Position(0, y), this.boardWidth, this.boardHeight));
        }

        for (int x = 0; x < this.boardWidth + 2; ++x) {
            this.cells.get(this.boardHeight + 1).add(new CellSide(new Position(x, this.boardHeight + 2), this.boardWidth, this.boardHeight));
        }
    }

    /**
     * Add CellSide at the end of each line, to do the right side of the board
     */
    private void initialiseRightSide() {
        for (int y = 1; y < this.boardHeight + 1; ++y) {
            this.cells.get(y).add(new CellSide(new Position(this.boardWidth + 2, y), this.boardWidth, this.boardHeight));
        }
    }

    /**
     * Initialize an line of free CelLFree on the board
     *
     * @param lineY the number of the line to set
     */
    private void initialiseEmptyLine(int lineY) {
        for (int x = 1; x <= this.boardWidth; ++x) {
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
        if (toSet.length != this.boardWidth) {
            System.err.println("Nombre d'arguments non valide : doit faire la largeur du plateau.");
        }

        for (int x = 1; x <= this.boardWidth; ++x) {
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
                    newCell = new CellFree(pos);
                    Hunter h = getHunterByName(toSet[x - 1]);
                    ((CellFree) newCell).setHunter(h);
                    assert h != null;
                    h.setCurrentCell(newCell);
            }

            this.cells.get(lineY).add(newCell);
        }
    }

    /**
     * Get the instance of a hunter from the list by his name
     *
     * @param name the name of the wanted hunter
     * @return the instance of the hunter from this.hunters
     */
    private Hunter getHunterByName(String name) {
        for (Hunter hunter : this.hunters) {
            if (hunter.toString().equals(name)) {
                return hunter;
            }
        }

        return null;
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

        for (int y = 1; y <= this.boardHeight; y++) {
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
     * <p>
     * His name is set to "name of the last hunter + 1"
     *
     * @return the new Hunter
     */
    public Hunter addHunter() {
        int name = this.hunters.size() + 'A';
        return addHunter(Character.toString((char) name));
    }

    /**
     * Add a hunter to the board
     *
     * @param name the name of the new Hunter
     * @return the new Hunter
     */
    public Hunter addHunter(String name) {
        Hunter newHunter = new Hunter(name);
        this.hunters.add(newHunter);
        return newHunter;
    }
}
