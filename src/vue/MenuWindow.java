package vue;

import controller.MenuController;

import javax.swing.*;
import java.awt.*;

/**
 * Treasure chase
 * <p>
 * Vue part of MVC
 * <p>
 * Designed with IntellijIDEA GUI Designer
 *
 * @author Nathanaël Houn
 */
public class MenuWindow extends JFrame {

    static public final Color COLOR_BG_ERROR = Color.ORANGE;
    static public final Color COLOR_BG_DEFAULT = new Color(238, 238, 238);

    private JPanel panelMain;

    private JButton buttonNewGame1;
    private JButton buttonLaunchEditor;
    private JFormattedTextField textFieldEditorWidth;
    private JFormattedTextField textFieldEditorHeight;
    private JTextPane textPaneEditorStatus;
    private JButton buttonQuit;
    private JLabel labelMaximumValue;

    public MenuWindow() {
        super("Menu | Treasure Chase — Nathanaël Houn");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        MenuController controller = new MenuController(this);
        buttonNewGame1.addActionListener(controller);
        buttonLaunchEditor.addActionListener(controller);
        buttonQuit.addActionListener(controller);
        textPaneEditorStatus.setBackground(COLOR_BG_DEFAULT);
        textPaneEditorStatus.setMargin( new Insets(10,10,10,10) );

        this.setContentPane(this.panelMain);
    }

    public JButton getButtonLaunchEditor() {
        return buttonLaunchEditor;
    }

    public JButton getButtonNewGame1() {
        return buttonNewGame1;
    }

    public JButton getButtonQuit() {
        return buttonQuit;
    }

    public JFormattedTextField getTextFieldEditorHeightValue() {
        return textFieldEditorHeight;
    }

    public JFormattedTextField getTextFieldEditorWidthValue() {
        return textFieldEditorWidth;
    }

    public JTextPane getTextPaneEditorStatus() {
        return textPaneEditorStatus;
    }

    public JLabel getLabelMaximumValue() { return labelMaximumValue; }
}
