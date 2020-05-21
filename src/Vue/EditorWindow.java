package Vue;

import Controller.EditorController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

/**
 * Treasure chase Editor
 *
 * @author Nathanaël Houn
 */

public class EditorWindow extends JFrame {

    private ArrayList<ArrayList<JLabel>> cellLabels;
    private JButton buttonReturnToMenu;
    private JButton buttonLaunchGame;
    private JLabel cellTreasure, hunter;
    private JLabel errorLabel;

    private EditorController controller;
    private MenuWindow menu;

    public EditorWindow(MenuWindow menu, int width, int height) {
        super("Éditeur | Treasure Chase — Nathanaël Houn");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.menu = menu;
        this.controller = new EditorController(this, width, height);

        Container container = this.getContentPane();

        // Main panels
        JPanel actionPanel = new JPanel();
        container.add("North", actionPanel);

        JPanel boardPanel = new JPanel(new GridLayout(height + 2, width + 2));
        container.add("Center", boardPanel);

        JPanel cellsPanel = new JPanel(new GridLayout(1, width + 2, 10, 50));
        container.add("South", cellsPanel);

        // Top panel    :  ------------------------------
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.PAGE_AXIS));

        this.buttonReturnToMenu = new JButton("Abandonner et retourner au menu");
        this.buttonReturnToMenu.addActionListener(this.controller);
        this.buttonReturnToMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.buttonLaunchGame = new JButton("Lancer la partie avec ce terrain");
        this.buttonLaunchGame.addActionListener(this.controller);
        this.buttonLaunchGame.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.errorLabel = new JLabel();
        this.errorLabel.setBackground(Color.ORANGE);
        this.errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        actionPanel.add(this.buttonReturnToMenu);
        actionPanel.add(this.buttonLaunchGame);
        actionPanel.add(this.errorLabel);


        // Middle panel :  ------------------------------
        Border border = LineBorder.createBlackLineBorder();

        this.cellLabels = new ArrayList<>();
        for (Integer y = 0; y < height + 2; y++) {
            ArrayList<JLabel> line = new ArrayList<>();
            for (Integer x = 0; x < width + 2; x++) {
                JLabel newCellJLabel = new JLabel();
                newCellJLabel.setHorizontalAlignment(JLabel.CENTER);
                newCellJLabel.setBorder(border);
                newCellJLabel.setOpaque(true);

                if ((x == 0 || x == width + 1)) {
                    newCellJLabel.setBackground(Color.RED);
                    if (!(y == 0 || y == height + 1)) {
                        newCellJLabel.setText(y.toString());
                    }
                } else if (y == 0 || y == height + 1) {
                    newCellJLabel.setBackground(Color.RED);
                    newCellJLabel.setText(x.toString());
                } else {
                    newCellJLabel.setTransferHandler(new TransferHandler("background"));
                    newCellJLabel.setBackground(Color.LIGHT_GRAY);
                }

                line.add(newCellJLabel);
                boardPanel.add(newCellJLabel);
            }
            this.cellLabels.add(line);
        }


        // Bottom panel :  ------------------------------
        TitledBorder emptyBorder = BorderFactory.createTitledBorder("Cases disponibles (glisser-déposer sur le terrain pour les placer)");
        cellsPanel.setPreferredSize(new Dimension(100, 70));
        cellsPanel.setBorder(emptyBorder);

        // TODO : forbid drop on these elements
        this.cellTreasure = new JLabel("Trésor");
        this.cellTreasure.setHorizontalAlignment(JLabel.CENTER);
        this.cellTreasure.setBorder(border);
        this.cellTreasure.setOpaque(true);
        this.cellTreasure.setBackground(Color.YELLOW);
        this.cellTreasure.setTransferHandler(new TransferHandler("background"));
        this.cellTreasure.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                TransferHandler handler = cellTreasure.getTransferHandler();
                handler.exportAsDrag(cellTreasure, e, TransferHandler.COPY); // TODO : move instead of copy
            }
        });

        this.hunter = new JLabel("Joueur");
        this.hunter.setHorizontalAlignment(JLabel.CENTER);
        this.hunter.setBorder(border);
        this.hunter.setOpaque(true);
        this.hunter.setBackground(Color.GRAY);
        this.hunter.setTransferHandler(new TransferHandler("background"));
        this.hunter.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                TransferHandler handler = hunter.getTransferHandler();
                handler.exportAsDrag(hunter, e, TransferHandler.COPY);
            }
        });

        int NUMBER_OF_CELLS = 4;

        cellsPanel.add(new JLabel());
        cellsPanel.add(cellTreasure);
        for (int i = NUMBER_OF_CELLS; i < width; ++i) {
            cellsPanel.add(new JLabel());
        }
        cellsPanel.add(hunter);
        cellsPanel.add(new JLabel());

    }

    /**
     * Start the editor
     */
    public void start() {
        this.setVisible(true);
    }

    public MenuWindow getMenu() {
        return menu;
    }

    public ArrayList<ArrayList<JLabel>> getCellLabels() {
        return cellLabels;
    }

    public JButton getButtonReturnToMenu() {
        return buttonReturnToMenu;
    }

    public JButton getButtonLaunchGame() {
        return buttonLaunchGame;
    }

    public void setErrorLabel(String str) {
        if (str == null || str.length() == 0) {
            this.errorLabel.setOpaque(false);
            this.errorLabel.setText("");
            return;
        }

        System.out.println(str);
        this.errorLabel.setOpaque(true);
        this.errorLabel.setText(str);
    }
}
