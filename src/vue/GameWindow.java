package vue;

import controller.GameController;
import model.Board;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Treasure chase
 * <p>
 * Vue part of MVC
 *
 * @author Nathanaël Houn
 */
public class GameWindow extends JFrame {

    private final MenuWindow menu;

    private final JButton buttonGoToMenu;
    private final JButton buttonNextRound;
    //    private final JButton buttonNewGame;
    private final ArrayList<ArrayList<JLabel>> cellLabels;
    private final JTextArea labelStatus;

    /**
     * Initialize the view
     * <p>
     * 3 main panels :
     * - top bar is the action panel with the controls
     * - central panel is the board panel, with the board and the players
     * - bottom bar is the status panel, with the status of each player
     */
    public GameWindow(MenuWindow menu, int boardNumber) {
        this(menu, new Board(boardNumber));
    }

    public GameWindow(MenuWindow menu, Board board) {
        super("Partie en cours | Treasure Chase — Nathanaël Houn");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.menu = menu;
        GameController controller = new GameController(this, board);
        this.cellLabels = new ArrayList<>();
        for (int y = 0; y < controller.getBoardHeight(); ++y) {
            this.cellLabels.add(new ArrayList<>());
        }

        Container container = this.getContentPane();

        // Main panels
        JPanel actionPanel = new JPanel();
        container.add("North", actionPanel);

        JPanel boardPanel = new JPanel(new GridLayout(controller.getBoardHeight() + 2, controller.getBoardWidth() + 2));
        container.add("Center", boardPanel);

        JPanel statusPanel = new JPanel();
        container.add("South", statusPanel);

        // Top panel : button ------------------------------
        this.buttonGoToMenu = new JButton("Retour au menu");
        this.buttonGoToMenu.addActionListener(controller);
        actionPanel.add(this.buttonGoToMenu);

        this.buttonNextRound = new JButton("Tour suivant");
        this.buttonNextRound.addActionListener(controller);
        actionPanel.add(this.buttonNextRound);

//        this.buttonNewGame = new JButton("Nouvelle partie");
//        this.buttonNewGame.addActionListener(this.controller);
//        actionPanel.add(this.buttonNewGame);


        // Middle panel : board ------------------------------
        Border border = LineBorder.createBlackLineBorder();

        for (int y = 0; y < controller.getBoardHeight() + 2; y++) {
            for (int x = 0; x < controller.getBoardWidth() + 2; x++) {
                JLabel newCellLabel = new JLabel();
                newCellLabel.setHorizontalAlignment(JLabel.CENTER);
                newCellLabel.setOpaque(true);
                newCellLabel.setBorder(border);

                if ((x == 0 || x == controller.getBoardWidth() + 1)) {
                    newCellLabel.setBackground(Color.RED);
                    if (!(y == 0 || y == controller.getBoardHeight() + 1)) {
                        newCellLabel.setText(Integer.toString(y));
                    }
                } else if (y == 0 || y == controller.getBoardHeight() + 1) {
                    newCellLabel.setBackground(Color.RED);
                    newCellLabel.setText(Integer.toString(x));
                } else {
                    this.cellLabels.get(y - 1).add(newCellLabel);
                }

                boardPanel.add(newCellLabel);
            }
        }

        controller.initialiseCellLabels();

        // Bottom panel : status ------------------------------
        this.labelStatus = new JTextArea();
        this.labelStatus.setEditable(true);
        this.labelStatus.setColumns(50);
        this.labelStatus.setBackground(statusPanel.getBackground());
        statusPanel.add(labelStatus);

        controller.updateStatusLabel();
    }

//    public JButton getButtonNewGame() {
//        return this.buttonNewGame;
//    }

    public JButton getButtonGoToMenu() {
        return this.buttonGoToMenu;
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

    public MenuWindow getMenu() {
        return this.menu;
    }
}
