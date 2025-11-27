package view.dialog;

import javax.swing.*;
import java.awt.*;

public class KeyMasterDialog {
    private final JDialog dialog;
    private final JPanel mainPanel;

    private JComboBox<String> keyCategoryComboBox;
    private JComboBox<String> algorithmTypeComboBox;
    private JComboBox<String> keySizeComboBox;
    private JTextField keyNameField;
    private JButton generateButton;

    private final String[] KEY_CATEGORIES = {"Симметричный", "Асимметричный"};
    public final String[] SYMMETRIC_ALGORITHMS = {"AES", "Blowfish"};
    public final String[] ASYMMETRIC_ALGORITHMS = {"RSA"};
    public final String[] SYMMETRIC_KEY_SIZES = {"256", "1024"};
    public final String[] ASYMMETRIC_KEY_SIZES = {"2048", "4096"};

    public KeyMasterDialog(JFrame owner) {
        dialog = new JDialog(owner, "Мастер управления ключами", true);
        mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 1. Имя ключа
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Имя ключа:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(keyNameField = new JTextField(20), gbc);

        // 2. Тип ключа
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Тип ключа:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(keyCategoryComboBox = new JComboBox<>(KEY_CATEGORIES), gbc);

        // 3. Алгоритм
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Алгоритм:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(algorithmTypeComboBox = new JComboBox<>(SYMMETRIC_ALGORITHMS), gbc);

        // 4. Размер ключа
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Размер ключа:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(keySizeComboBox = new JComboBox<>(SYMMETRIC_KEY_SIZES), gbc);

        // Кнопки
        JPanel buttons = new JPanel();
        buttons.add(generateButton = new JButton("Сгенерировать"));

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttons, gbc);

        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(owner);

    }

    public void show(){
        dialog.setVisible(true);
    }

    public String[] getSYMMETRIC_ALGORITHMS() {
        return SYMMETRIC_ALGORITHMS;
    }

    public String[] getASYMMETRIC_ALGORITHMS() {
        return ASYMMETRIC_ALGORITHMS;
    }

    public String[] getSYMMETRIC_KEY_SIZES() {
        return SYMMETRIC_KEY_SIZES;
    }

    public String[] getASYMMETRIC_KEY_SIZES() {
        return ASYMMETRIC_KEY_SIZES;
    }

    public JTextField getKeyNameField() { return keyNameField; }
    public JComboBox<String> getKeyCategoryComboBox() { return keyCategoryComboBox; }
    public JComboBox<String> getAlgorithmTypeComboBox() { return algorithmTypeComboBox; }
    public JComboBox<String> getKeySizeComboBox() { return keySizeComboBox; }
    public JButton getGenerateButton() { return generateButton; }
}