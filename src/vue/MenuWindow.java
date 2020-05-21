package vue;

import controller.MenuController;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;

/**
 * Treasure chase menu
 *
 * @author Nathanaël Houn
 */
public class MenuWindow extends JFrame {

    private final JButton buttonNewGame1;
    private final JFormattedTextField textFieldEditorHeight;
    private final JFormattedTextField textFieldEditorWidth;
    private final JButton buttonLaunchEditor;

    public MenuWindow() {
        super("Menu | Treasure Chase — Nathanaël Houn");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuController controller = new MenuController(this);

        Container container = this.getContentPane();

        // Main panels
        JPanel actionPanel = new JPanel();
        container.add("North", actionPanel);

        JPanel editorPanel = new JPanel();
        container.add("Center", editorPanel);

        JPanel statusPanel = new JPanel();
        container.add("South", statusPanel);

        // Top panel    :  ------------------------------
        this.buttonNewGame1 = new JButton("Lancer la partie 1");
        this.buttonNewGame1.addActionListener(controller);
        actionPanel.add(this.buttonNewGame1);

        // Middle panel :  ------------------------------
        this.textFieldEditorHeight = new JFormattedTextField(createFormatter("##"));
        this.textFieldEditorHeight.setValue("10");
        editorPanel.add(this.textFieldEditorHeight);

        this.textFieldEditorWidth = new JFormattedTextField(createFormatter("##"));
        this.textFieldEditorWidth.setValue("10");
        editorPanel.add(this.textFieldEditorWidth);

        this.buttonLaunchEditor = new JButton("Lancer l'éditeur");
        this.buttonLaunchEditor.addActionListener(controller);
        actionPanel.add(this.buttonLaunchEditor);

        // Bottom panel :  ------------------------------
    }

    /**
     * Start the menu
     */
    public void start() {
        this.setVisible(true);
    }

    public JButton getButtonNewGame1() {
        return this.buttonNewGame1;
    }

    public JButton getButtonLaunchEditor() {
        return this.buttonLaunchEditor;
    }

    public JFormattedTextField getTextFieldEditorHeight() {
        return this.textFieldEditorHeight;
    }

    public JFormattedTextField getTextFieldEditorWidth() {
        return this.textFieldEditorWidth;
    }

    private MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

}