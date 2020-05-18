import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * Treasure chase
 * <p>
 * Vue part of MVC
 *
 * @author Nathanaël Houn
 */
public class Window extends JFrame {

    private Controller controller;

    private JButton buttonNextRound;
    private ArrayList<ArrayList<JLabel>> cellLabels;

    /**
     * Initialize the view
     * <p>
     * 3 main panels :
     * - top bar is the action panel with the controls
     * - central panel is the board panel, with the board and the players
     * - bottom bar is the status panel, with the status of each player
     */
    public Window(int boardHeight, int boardWidth, int numberOfHunters) {
        super("Treasure Chase — Nathanaël Houn");
        this.setSize(600, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.controller = new Controller(this, boardHeight, boardWidth, numberOfHunters);
        this.cellLabels = new ArrayList<ArrayList<JLabel>>();
        for (int y = 0; y < boardHeight; ++y) {
            this.cellLabels.add(new ArrayList<JLabel>());
        }

        Container container = this.getContentPane();

        // Main panels
        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(Color.BLUE);
        container.add("North", actionPanel);

        JPanel boardPanel = new JPanel(new GridLayout(boardHeight + 2, boardWidth + 2));
        boardPanel.setBackground(Color.GREEN);
        container.add("Center", boardPanel);

        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.YELLOW);
        container.add("South", statusPanel);

        // Top panel : button ------------------------------
        this.buttonNextRound = new JButton("Tour suivant");
        this.buttonNextRound.addActionListener(this.controller);
        actionPanel.add(this.buttonNextRound);

        // Middle panel : board ------------------------------
        Border border = LineBorder.createBlackLineBorder();

        for (Integer y = 0; y < boardHeight + 2; y++) {
            for (Integer x = 0; x < boardWidth + 2; x++) {
                JLabel newCellLabel = new JLabel();
                newCellLabel.setHorizontalAlignment(JLabel.CENTER);
                newCellLabel.setOpaque(true);
                newCellLabel.setBorder(border);

                if ((x == 0 || x == boardWidth + 1)) {
                    newCellLabel.setBackground(Color.RED);
                    if (!(y == 0 || y == boardHeight + 1)) {
                        newCellLabel.setText(y.toString());
                    }
                } else if (y == 0 || y == boardHeight + 1) {
                    newCellLabel.setBackground(Color.RED);
                    newCellLabel.setText(x.toString());
                } else {
                    this.cellLabels.get(y - 1).add(newCellLabel);
                }

                boardPanel.add(newCellLabel);
            }
        }

        this.controller.updateCellsLabels();
    }

    public JLabel getCellLabel(int x, int y) {
        return this.cellLabels.get(y).get(x);
    }

    /**
     * Start the game
     */
    public void start() {
        this.setVisible(true);
    }
}
