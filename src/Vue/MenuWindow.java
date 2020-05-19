package Vue;

import Controller.GameController;
import Controller.MenuController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Treasure chase menu
 *
 * @author Nathanaël Houn
 */
public class MenuWindow  extends JFrame {

    public final JButton buttonNewGame1;
    private MenuController controller;

    MenuWindow() {
        super("Menu | Treasure Chase — Nathanaël Houn");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.controller = new MenuController(this);

        Container container = this.getContentPane();

        // Main panels
        JPanel actionPanel = new JPanel();
        container.add("North", actionPanel);

        JPanel boardPanel = new JPanel();
        container.add("Center", boardPanel);

        JPanel statusPanel = new JPanel();
        container.add("South", statusPanel);

        // Top panel    :  ------------------------------
        this.buttonNewGame1 = new JButton("Lancer la partie 1");
        this.buttonNewGame1.addActionListener(this.controller);
        actionPanel.add(this.buttonNewGame1);

        // Middle panel :  ------------------------------

        // Bottom panel :  ------------------------------
    }

    /**
     * Start the menu
     */
    public void start() {
        this.setVisible(true);
    }}
