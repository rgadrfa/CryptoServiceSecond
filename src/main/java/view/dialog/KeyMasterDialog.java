package view.dialog;

import javax.swing.*;
import java.awt.*;

public class KeyMasterDialog {
    private final JDialog dialog;
    private final JPanel mainPanel;
    private final JFrame owner;

    private JList<String> keysList;
    private DefaultListModel<String> keysListModel;
    private JButton generateButton;
    private JButton deleteKeyButton;
    private JComboBox<String> keyCategoryComboBox;
    private JComboBox<String> algorithmTypeComboBox;
    private JComboBox<String> keySizeComboBox;
    private JTextField keyNameField;
    private JTextArea keyInfoArea;

    private static final String DIALOG_TITLE = "Мастер управления ключами";
    private static final String LABEL_GENERATE = "Сгенерировать";
    private static final String LABEL_DELETE_KEY = "Удалить ключ";
    private static final String LABEL_KEY_NAME = "Имя ключа:";
    private static final String LABEL_KEY_CATEGORY = "Тип ключа:";
    private static final String LABEL_ALGORITHM_TYPE = "Алгоритм:";
    private static final String LABEL_KEY_SIZE = "Размер ключа:";
    private static final String LABEL_KEYS_LIST = "Список ключей";
    private static final String LABEL_KEY_INFO = "Информация о ключе";

    private static final String[] KEY_CATEGORIES = {"Симметричный", "Асимметричный"};
    private static final String[] SYMMETRIC_ALGORITHMS = {"AES", "Blowfish"};
    private static final String[] ASYMMETRIC_ALGORITHMS = {"RSA"};
    private static final String[] ASYMMETRIC_KEY_SIZES = {"2048", "4096"};
    private static final String[] SYMMETRIC_KEY_SIZES = {"256", "1024"};

    public KeyMasterDialog(JFrame owner) {
        this.owner = owner;
        this.dialog = new JDialog(owner, true);
        this.mainPanel = new JPanel(new BorderLayout(10, 10));
        setupDialog();
    }

    private void setupDialog() {
        dialog.add(mainPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(createLeftPanel(), BorderLayout.WEST);
        mainPanel.add(createKeyInfoPanel(), BorderLayout.CENTER);

        setupWindowProperties();
    }

    private void setupWindowProperties() {
        dialog.setTitle(DIALOG_TITLE);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(750, 450);
        dialog.setLocationRelativeTo(owner);
        dialog.setResizable(true);
        dialog.setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.add(createKeysListPanel(), BorderLayout.CENTER);
        leftPanel.add(createControlPanel(), BorderLayout.SOUTH);
        return leftPanel;
    }

    private JPanel createKeysListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(250, 300));

        keysListModel = new DefaultListModel<>();
        keysList = new JList<>(keysListModel);
        keysList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panel.add(new JScrollPane(keysList), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panel.add(createParamsPanel(), BorderLayout.CENTER);
        panel.add(createButtonPanel(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createParamsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        addLabelAndComponent(panel, gbc, LABEL_KEY_NAME, keyNameField = new JTextField(), 0, 2);
        addLabelAndComponent(panel, gbc, LABEL_KEY_CATEGORY, keyCategoryComboBox = new JComboBox<>(KEY_CATEGORIES), 2, 1);
        addLabelAndComponent(panel, gbc, LABEL_ALGORITHM_TYPE, algorithmTypeComboBox = new JComboBox<>(SYMMETRIC_ALGORITHMS), 3, 1);
        addLabelAndComponent(panel, gbc, LABEL_KEY_SIZE, keySizeComboBox = new JComboBox<>(SYMMETRIC_KEY_SIZES), 4, 1);

        return panel;
    }

    private void addLabelAndComponent(JPanel panel, GridBagConstraints gbc, String labelText, JComponent component, int row, int width) {
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1; gbc.gridy = row;
        gbc.gridwidth = width;
        panel.add(component, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 5, 0));
        generateButton = new JButton(LABEL_GENERATE);
        deleteKeyButton = new JButton(LABEL_DELETE_KEY);

        panel.add(generateButton);
        panel.add(deleteKeyButton);

        return panel;
    }

    private JPanel createKeyInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        keyInfoArea = new JTextArea(15, 30);
        keyInfoArea.setEditable(false);

        panel.add(new JScrollPane(keyInfoArea), BorderLayout.CENTER);
        return panel;
    }

    public JList<String> getKeysList() {
        return keysList;
    }
}