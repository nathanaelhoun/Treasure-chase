import java.util.ArrayList;

/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 */
public class Board {
    private final int boardHeight;
    private final int boardWidth;
    private ArrayList<ArrayList<Cell>> cells;
    private CellTreasure treasure;
    private ArrayList<Hunter> hunters;

    public Board(int boardHeight, int boardWidth) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;

        this.cells = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < this.boardHeight + 2; ++i) {
            this.cells.add(new ArrayList<Cell>());
        }

        this.hunters = new ArrayList<Hunter>();
        this.treasure = null;
    }

    public int getBoardHeight() {
        return this.boardHeight;
    }

    public int getBoardWidth() {
        return this.boardWidth;
    }

    public String getCellString(int x, int y) {
        return this.cells.get(y).get(x).toString();
    }

    @Override
    public String toString() {
        String board = "";
        for (ArrayList<Cell> line : cells) {
            for (Cell cell : line) {
                board += cell.toString();
            }
            board += '\n';
        }

        return board;
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
        for (ArrayList<Cell> line : cells) {
            for (Cell c : line) {
                c.setTreasure(this.treasure);
            }
        }
    }

    /**
     * Initialise the hunter array but don't place the hunters on the board
     *
     * @param numberOfHunters the number of hunter to generate
     */
    public void initialiseHunters(int numberOfHunters) {
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
     * @return the instance of the winner Hunter
     */
    public Hunter getWinner() {
        return this.treasure.getWinner();
    }

    /**************************************************************************
     * 							Utilities
     *************************************************************************/

    /**
     * Find the position of the treasure on the board
     *
     * @return the instance of the treasure from the board
     */
    private CellTreasure getTreasure() {
        for (ArrayList<Cell> line : cells) {
            for (Cell cell : line) {
                if (cell.toString() == "T") {
                    return (CellTreasure) cell;
                }
            }
        }

        return null;
    }

    private void initialiseTopLeftAndBottomSides() {
        for (int x = 0; x < this.boardWidth + 2; ++x) {
            this.cells.get(0).add(new CellSide(new Position(x, 0)));
        }

        for (int y = 1; y < this.boardHeight + 1; ++y) {
            this.cells.get(y).add(new CellSide(new Position(0, y)));
        }

        for (int x = 0; x < this.boardWidth + 2; ++x) {
            this.cells.get(this.boardHeight + 1).add(new CellSide(new Position(x, this.boardHeight + 2)));
        }
    }

    private void initialiseRightSide() {
        for (int y = 1; y < this.boardHeight + 1; ++y) {
            this.cells.get(y).add(new CellSide(new Position(this.boardWidth + 2, y)));
        }
    }


    private void initialiseEmptyLine(int lineY) {
        for (int x = 1; x <= this.boardWidth; ++x) {
            this.cells.get(lineY).add(new CellFree(new Position(x, lineY)));
        }

    }

    private void initialiseLine(int lineY, String[] toSet) {
        if (toSet.length != this.boardWidth) {
            System.err.println("Nombre d'arguments non valide : doit faire la largeur du plateau.");
        }

        for (int x = 1; x <= this.boardWidth; ++x) {
            Position pos = new Position(x, lineY);
            Cell newCell;

            switch (toSet[x - 1]) {
                case "#V":
                    newCell = new CellStone(pos, CellStone.Orientation.VERTICAL);
                    break;

                case "#H":
                    newCell = new CellStone(pos, CellStone.Orientation.HORIZONTAL);
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
                    h.setCurrentCell((CellFree) newCell);
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
        int size = this.hunters.size();
        for (int i = 0; i < size; ++i) {
            if (this.hunters.get(i).toString().equals(name)) {
                return this.hunters.get(i);
            }
        }

        return null;
    }
}
