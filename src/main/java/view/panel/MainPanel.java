package view.panel;

import view.MainFrame;
import view.dialog.KeyMasterDialog;
import view.interfaces.IPanel;

import javax.swing.*;
import java.awt.*;

public class MainPanel implements IPanel {
    private final JPanel mainPanel;

    private JComboBox<String> cryptoTypeCombo;
    private JComboBox<String> algorithmCombo;
    private JComboBox<String> modeCombo;
    private JComboBox<String> paddingCombo;
    private JTextField filePathField;
    private JTextField keyPathField;
    private JButton fileSelectButton;
    private JButton keySelectButton;
    private JButton keyMasterButton;
    private JButton encryptButton;
    private JButton decryptButton;

    private final String LABEL_CRYPTO_TYPE = "Тип шифрования:";
    private final String LABEL_ALGORITHM = "Алгоритм:";
    private final String LABEL_MODE = "Режим работы:";
    private final String LABEL_PADDING = "Padding:";
    private final String LABEL_FILE = "Файл:";
    private final String LABEL_KEY_FILE = "Файл ключа:";

    private final String BUTTON_SELECT_FILE = "Выбрать";
    private final String BUTTON_SELECT_KEY = "Выбрать ключ";
    private final String BUTTON_KEY_MASTER = "Мастер ключей";
    private final String BUTTON_ENCRYPT = "Зашифровать";
    private final String BUTTON_DECRYPT = "Расшифровать";

    private final String[] CRYPTO_TYPES = {"Симметричное", "Асимметричное"};
    private final String[] SYMMETRIC_ALGORITHMS = {"AES", "Blowfish"};
    private final String[] ASYMMETRIC_ALGORITHMS = {"RSA"};
    private final String[] MODES = {"ECB", "CBC", "CFB", "OFB", "CTR"};
    private final String[] PADDINGS = {"PKCS5Padding", "PKCS7Padding", "NoPadding"};

    public MainPanel() {
        mainPanel = new JPanel();
        setupPanel();
    }

    private void setupPanel() {
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        mainPanel.add(createControlPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        addComboBoxRow(panel, gbc, LABEL_CRYPTO_TYPE, cryptoTypeCombo = new JComboBox<>(CRYPTO_TYPES), 0);
        addComboBoxRow(panel, gbc, LABEL_ALGORITHM, algorithmCombo = new JComboBox<>(SYMMETRIC_ALGORITHMS), 1);
        addComboBoxRow(panel, gbc, LABEL_MODE, modeCombo = new JComboBox<>(MODES), 2);
        addComboBoxRow(panel, gbc, LABEL_PADDING, paddingCombo = new JComboBox<>(PADDINGS), 3);
        addFileSelectionRow(panel, gbc, 4);
        addKeyFileSelectionRow(panel, gbc, 5);

        return panel;
    }

    private void addComboBoxRow(JPanel panel, GridBagConstraints gbc, String label, JComboBox<String> comboBox, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(comboBox, gbc);
    }

    private void addFileSelectionRow(JPanel panel, GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(LABEL_FILE), gbc);

        gbc.gridx = 1;
        JPanel filePanel = new JPanel(new BorderLayout(5, 0));
        filePathField = new JTextField();
        filePathField.setEditable(false);
        fileSelectButton = new JButton(BUTTON_SELECT_FILE);

        filePanel.add(filePathField, BorderLayout.CENTER);
        filePanel.add(fileSelectButton, BorderLayout.EAST);
        panel.add(filePanel, gbc);
    }

    private void addKeyFileSelectionRow(JPanel panel, GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(LABEL_KEY_FILE), gbc);

        gbc.gridx = 1;
        JPanel keyPanel = new JPanel(new BorderLayout(5, 0));
        keyPathField = new JTextField();
        keyPathField.setEditable(false);
        keySelectButton = new JButton(BUTTON_SELECT_KEY);

        keyPanel.add(keyPathField, BorderLayout.CENTER);
        keyPanel.add(keySelectButton, BorderLayout.EAST);
        panel.add(keyPanel, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        keyMasterButton = new JButton(BUTTON_KEY_MASTER);
        encryptButton = new JButton(BUTTON_ENCRYPT);
        decryptButton = new JButton(BUTTON_DECRYPT);

        panel.add(keyMasterButton);
        panel.add(encryptButton);
        panel.add(decryptButton);

        return panel;
    }

    public boolean isPathFiledEmpty() {
        return filePathField.getText().trim().isEmpty();
    }

    public boolean isKeyPathEmpty() {
        return keyPathField.getText().trim().isEmpty();
    }

    // Геттеры
    public JComboBox<String> getAlgorithmCombo() { return algorithmCombo; }
    public JComboBox<String> getModeCombo() { return modeCombo; }
    public JComboBox<String> getPaddingCombo() { return paddingCombo; }
    public JComboBox<String> getCryptoTypeCombo() { return cryptoTypeCombo; }
    public JButton getFileSelectButton() { return fileSelectButton; }
    public JButton getKeySelectButton() { return keySelectButton; }
    public JButton getEncryptButton() { return encryptButton; }
    public JButton getDecryptButton() { return decryptButton; }
    public JButton getKeyMasterButton() { return keyMasterButton; }
    public JTextField getFilePathField() { return filePathField; }
    public JTextField getKeyPathField() { return keyPathField; }

    public String[] getCRYPTO_TYPES() {
        return CRYPTO_TYPES;
    }

    public String[] getASYMMETRIC_ALGORITHMS() {
        return ASYMMETRIC_ALGORITHMS;
    }

    public String[] getSYMMETRIC_ALGORITHMS() {
        return SYMMETRIC_ALGORITHMS;
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }
}