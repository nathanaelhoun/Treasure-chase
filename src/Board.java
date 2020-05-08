import java.util.ArrayList;

/**
 * Treasure chase
 * @author Nathanaël Houn
 *
 */
public class Board {
    private final int boardHeight;
    private final int boardWidth;
    private ArrayList<ArrayList<Cell>> cells;
    private ArrayList<Hunter> hunters;

    public Board(int boardHeight, int boardWidth) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;

        this.cells = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < this.boardHeight + 2; ++i) {
            this.cells.add(new ArrayList<Cell>());
        }

        this.hunters = new ArrayList<Hunter>();
    }

    public void initialiseBoard() {
        initialiseTopLeftAndBottomSides();

        initialiseEmptyLine(1);
        initialiseEmptyLine(2);

        initialiseLine(3, new String[] {".", ".", ".", "#V", ".", ".", ".", ".", ".", ".", ".", "."});
        initialiseLine(4, new String[] {".", "T", ".", "#V", ".", ".", ".", ".", ".", "A", ".", "."});
        initialiseLine(5, new String[] {".", ".", ".", "#V", ".", ".", ".", ".", ".", ".", ".", "."});
        initialiseLine(6, new String[] {".", ".", ".", "#V", ".", ".", ".", ".", ".", ".", ".", "."});
        initialiseLine(7, new String[] {".", ".", ".", "#V", ".", ".", "#H", "#H", "#H", ".", ".", "."});
        initialiseLine(8, new String[] {".", ".", ".", "#V", ".", ".", ".", "C", ".", ".", ".", "."});
        initialiseLine(9, new String[] {".", ".", ".", "#V", ".", ".", ".", ".", "B", ".", ".", "."});

        initialiseEmptyLine(10);
        initialiseEmptyLine(11);
        initialiseEmptyLine(12);

        initialiseRightSide();
    }

    public void initialiseHunters(int numberOfHunters) {
        for (int i = 0; i < numberOfHunters; i++) {
            String name = "" + (char) (i + 'A');
            this.hunters.add(new Hunter(name));
        }
    }

    public void printBoard() {
        for (ArrayList<Cell> line : cells) {
            for (Cell cell : line) {
                System.out.print(cell.toString());
            }
            System.out.print('\n');
        }
    }

    /**************************************************************************
     * 							Utilities
     *************************************************************************/

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

            switch(toSet[x - 1]) {
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
                    h.setPosition(pos.getX(), pos.getY());
            }

            this.cells.get(lineY).add(newCell);
        }
    }

    private Hunter getHunterByName(String name) {
        int size = this.hunters.size();
        for (int i = 0; i < size; ++i) {
            if (this.hunters.get(i).getName().equals(name)) {
                return this.hunters.get(i);
            }
    }

        return null;
    }
}
