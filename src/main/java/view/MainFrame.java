package view;

import view.interfaces.IParentPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame implements IParentPanel {
    public final JFrame mainFrame;
    private final JPanel mainPanel;

    private static final String ERROR_TITLE = "Ошибка";
    private static final String INFORMATION_TITLE = "Информация";

    private static MainFrame instance;

    private MainFrame() {
        mainFrame = new JFrame();
        mainFrame.setLayout(new BorderLayout());
        mainPanel = new JPanel(new BorderLayout());
    }

    public void build() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    public void showWindow() {
        mainFrame.setVisible(true);
    }

    public void setWindowSize(int width, int height) {
        mainFrame.setBounds(new Rectangle(width, height));
    }

    public void setWindowName(String windowName) {
        mainFrame.setTitle(windowName);
    }

    public static void showError(String message){
        JOptionPane.showMessageDialog(MainFrame.getInstance().mainFrame,
                message,
                ERROR_TITLE,
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static void showInformation(String message){
        JOptionPane.showMessageDialog(MainFrame.getInstance().mainFrame,
                message,
                INFORMATION_TITLE,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void setPanel(JPanel mainPanel) {
        this.mainPanel.add(mainPanel);
        mainFrame.add(this.mainPanel);
    }

    @Override
    public JPanel getPanel() {
        return null;
    }
}
