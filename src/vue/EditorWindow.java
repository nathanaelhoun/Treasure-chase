package vue;

import controller.EditorController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

/**
 * Treasure chase Editor
 *
 * @author Nathanaël Houn
 */

public class EditorWindow extends JFrame {

    private final ArrayList<ArrayList<JLabel>> cellLabels;
    private final JButton buttonReturnToMenu;
    private final JButton buttonLaunchGame;
    private final JLabel cellTreasure;
    private final JLabel cellStone;
    private final JLabel hunter;
    private final JLabel errorLabel;

    private final MenuWindow menu;

    public EditorWindow(MenuWindow menu, int width, int height) {
        super("Éditeur | Treasure Chase — Nathanaël Houn");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.menu = menu;
        EditorController controller = new EditorController(this, width, height);

        Container container = this.getContentPane();

        // Main panels
        JPanel actionPanel = new JPanel();
        container.add("North", actionPanel);

        JPanel boardPanel = new JPanel(new GridLayout(height + 2, width + 2));
        container.add("Center", boardPanel);

        JPanel cellsPanel = new JPanel(new GridLayout(1, 9, 10, 50));
        container.add("South", cellsPanel);

        // Top panel    :  ------------------------------
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.PAGE_AXIS));

        this.buttonReturnToMenu = new JButton("Abandonner et retourner au menu");
        this.buttonReturnToMenu.addActionListener(controller);
        this.buttonReturnToMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.buttonLaunchGame = new JButton("Lancer la partie avec ce terrain");
        this.buttonLaunchGame.addActionListener(controller);
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
        for (int y = 0; y < height + 2; y++) {
            ArrayList<JLabel> line = new ArrayList<>();
            for (int x = 0; x < width + 2; x++) {
                JLabel newCellJLabel = new JLabel();
                newCellJLabel.setHorizontalAlignment(JLabel.CENTER);
                newCellJLabel.setBorder(border);
                newCellJLabel.setOpaque(true);
                newCellJLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        newCellJLabel.setBackground(Color.LIGHT_GRAY);
                    }
                });

                if ((x == 0 || x == width + 1)) {
                    newCellJLabel.setBackground(Color.RED);
                    if (!(y == 0 || y == height + 1)) {
                        newCellJLabel.setText(Integer.toString(y));
                    }
                } else if (y == 0 || y == height + 1) {
                    newCellJLabel.setBackground(Color.RED);
                    newCellJLabel.setText(Integer.toString(x));
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
        TitledBorder emptyBorder = BorderFactory.createTitledBorder("Cases disponibles (glisser-déposer sur le terrain pour les placer) (cliquer sur une case pour la supprimer)");
        cellsPanel.setPreferredSize(new Dimension(100, 70));
        cellsPanel.setBorder(emptyBorder);

        this.cellTreasure = new JLabel("Trésor");
        this.cellTreasure.setHorizontalAlignment(JLabel.CENTER);
        this.cellTreasure.setBorder(border);
        this.cellTreasure.setOpaque(true);
        this.cellTreasure.setBackground(Color.ORANGE);
        this.cellTreasure.setTransferHandler(new TransferHandler("background"));
        this.cellTreasure.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                cellTreasure.setBackground(Color.ORANGE);
                TransferHandler handler = cellTreasure.getTransferHandler();
                handler.exportAsDrag(cellTreasure, e, TransferHandler.COPY);
            }
        });

        this.cellStone = new JLabel("Mur");
        this.cellStone.setHorizontalAlignment(JLabel.CENTER);
        this.cellStone.setBorder(border);
        this.cellStone.setOpaque(true);
        this.cellStone.setBackground(Color.BLUE);
        this.cellStone.setTransferHandler(new TransferHandler("background"));
        this.cellStone.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                cellStone.setBackground(Color.BLUE);
                TransferHandler handler = cellStone.getTransferHandler();
                handler.exportAsDrag(cellStone, e, TransferHandler.COPY);
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
                hunter.setBackground(Color.GRAY);
                TransferHandler handler = hunter.getTransferHandler();
                handler.exportAsDrag(hunter, e, TransferHandler.COPY);
            }
        });

        cellsPanel.add(new JLabel());
        cellsPanel.add(cellTreasure);
        cellsPanel.add(new JLabel());
        cellsPanel.add(new JLabel());
        cellsPanel.add(cellStone);
        cellsPanel.add(new JLabel());
        cellsPanel.add(new JLabel());
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
