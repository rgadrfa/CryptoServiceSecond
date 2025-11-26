package view.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AuthDialog {
    private final JDialog dialog;
    private final JPanel mainPanel;
    private final JFrame owner;

    // Компоненты для аутентификации
    private JComboBox<String> keyStoreComboBox;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createNewButton;
    private JCheckBox showPasswordCheckBox;

    // Константы
    private final String DIALOG_TITLE = "Аутентификация в Key Store";
    private final String LABEL_KEYSTORE = "Key Store:";
    private final String LABEL_PASSWORD = "Пароль:";
    private final String LABEL_LOGIN_BUTTON = "Войти";
    private final String LABEL_CREATE_NEW_BUTTON = "Создать новый";
    private final String LABEL_SHOW_PASSWORD = "Показать пароль";

    public AuthDialog(JFrame owner) {
        this.owner = owner;
        this.dialog = new JDialog(owner, true);
        this.mainPanel = new JPanel(new BorderLayout(10, 10));
        dialog.add(mainPanel);

        setup();
        setupWindow();
    }

    public void showDialog(){
        dialog.setVisible(true);
    }

    private void setup() {
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.add(createAuthPanel(), BorderLayout.CENTER);
    }

    private void setupWindow() {
        dialog.setTitle(DIALOG_TITLE);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(450, 240);
        dialog.setLocationRelativeTo(owner);
        dialog.setResizable(false);

        // Обработка закрытия окна
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private JPanel createAuthPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(new JLabel(LABEL_KEYSTORE), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        keyStoreComboBox = new JComboBox<>();
        keyStoreComboBox.setEditable(true);
        fieldsPanel.add(keyStoreComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        fieldsPanel.add(new JLabel(LABEL_PASSWORD), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(20);
        fieldsPanel.add(passwordField, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.gridwidth = 2;
        showPasswordCheckBox = new JCheckBox(LABEL_SHOW_PASSWORD);
        fieldsPanel.add(showPasswordCheckBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        loginButton = new JButton(LABEL_LOGIN_BUTTON);
        createNewButton = new JButton(LABEL_CREATE_NEW_BUTTON);

        buttonPanel.add(loginButton);
        buttonPanel.add(createNewButton);

        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public JDialog getDialog() {
        return dialog;
    }

    public JComboBox<String> getKeyStoreComboBox() {
        return keyStoreComboBox;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getCreateNewButton() {
        return createNewButton;
    }

    public JCheckBox getShowPasswordCheckBox() {
        return showPasswordCheckBox;
    }

    public String getSelectedKeyStore() {
        Object selected = keyStoreComboBox.getSelectedItem();
        return selected != null ? selected.toString().trim() : "";
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }
}