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
    private JButton buttonNewGame;
    private ArrayList<ArrayList<JLabel>> cellLabels;
    private JTextArea labelStatus;

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
        this.setSize(600, 600);
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

        this.buttonNewGame = new JButton("Nouvelle partie");
        this.buttonNewGame.addActionListener(this.controller);
        actionPanel.add(this.buttonNewGame);


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

        // Bottom panel : status ------------------------------
        this.labelStatus = new JTextArea();
        this.labelStatus.setEditable(true);
        statusPanel.add(labelStatus);

        this.controller.updateStatusLabel();
    }

    public JButton getButtonNewGame() {
        return this.buttonNewGame;
    }

    public JButton getButtonNextRound() {
        return this.buttonNextRound;
    }

    public JLabel getCellLabel(int x, int y) {
        return this.cellLabels.get(y).get(x);
    }

    public JTextArea getLabelStatus() {
        return this.labelStatus;
    }

    /**
     * Start the game
     */
    public void start() {
        this.setVisible(true);
    }
}
