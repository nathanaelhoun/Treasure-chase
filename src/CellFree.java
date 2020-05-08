/**
 * Treasure chase
 *
 * @author Nathanaël Houn
 *
 */
public class CellFree extends Cell {

    private Hunter currentHunter;

    /**
     * @param p Cell position in the map
     */
    public CellFree(Position p) {
        super(p);
        this.currentHunter = null;
    }

    @Override
    public void process(Hunter h) {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        if (this.currentHunter == null) {
            return "·";
        }

        return currentHunter.toString();
    }

    public void setHunter (Hunter h) {
        if (this.currentHunter != null) {
            System.err.println("Il y a déjà un Hunter dans cette case");
        }

        this.currentHunter = h;
    }

    public Hunter popHunter() {
        Hunter temp = this.currentHunter;
        this.currentHunter = null;
        return temp;
    }

}
