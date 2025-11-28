package controller;

import model.crypto.CryptoFactory;
import model.crypto.CryptoService;
import model.crypto.controllers.AsymmetricAlgorithm;
import model.crypto.controllers.SymmetricAlgorithm;
import model.crypto.interfaces.ICryptoAsymmetricAlgorithm;
import model.crypto.interfaces.ICryptoSymmetricAlgorithm;
import model.file.Data;
import model.file.FileModel;
import model.file.enums.BasePath;
import model.file.enums.FileExtension;
import model.file.interfaces.IFileController;
import model.file.interfaces.IPemFileReader;
import model.file.pem_decoder.PemFileReaderController;
import model.file.util.RandomNamer;
import view.MainFrame;
import view.dialog.KeyMasterDialog;
import view.dialog.PathDialog;
import view.panel.MainPanel;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.security.PrivateKey;
import java.security.PublicKey;

public class MainPanelController {
    private final MainPanel view;
    private final FileModel fileModel;

    private IFileController fileController;
    private IPemFileReader pemFileReader;

    private CryptoService<?> cryptoService;

    private static final String SYMMETRIC_CRYPTO = "Симметричное";

    public MainPanelController(MainPanel view, FileModel fileModel){
        this.view = view;
        this.fileModel = fileModel;

        fileController = fileModel.getFileController();
        pemFileReader = fileModel.getPemFileReader();

        initController();
        updateAlgorithms();
    }

    private void initController() {
        view.getFileSelectButton().addActionListener(_ -> openFileSelect());
        view.getKeySelectButton().addActionListener(_ ->
                openFileSelect(FileExtension.EXTENSION_KEY_FILE.getName())
        );
        view.getCryptoTypeCombo().addActionListener(_ -> updateAlgorithms());

        view.getKeyMasterButton().addActionListener(_ -> openKeyMaster());

        view.getEncryptButton().addActionListener(_ -> encrypt());
        view.getDecryptButton().addActionListener(_ -> decrypt());
    }

    private void setupCryptoService() {
        String cryptoType = getSelectedCryptoType();
        try {
            if (cryptoType.equals(SYMMETRIC_CRYPTO)) {
                cryptoService = CryptoFactory.create(new SymmetricAlgorithm(buildSymmetricTransform()));
            } else {
                cryptoService = CryptoFactory.create(new AsymmetricAlgorithm(buildAsymmetricTransform()));
            }
        } catch (Exception e) {
            MainFrame.showError("Ошибка создания алгоритма: " + e.getMessage());
        }
    }

    //region encode
    private void encrypt() {
        if (view.isPathFiledEmpty()) {
            MainFrame.showError("Не указан путь к файлу");
            return;
        }
        if (view.isKeyPathEmpty()) {
            MainFrame.showError("Не указан путь к ключу");
            return;
        }

        try {
            setupCryptoService();

            Data fileData = fileController.read(view.getFilePathField().getText());
            String cryptoType = getSelectedCryptoType();

            Data result;
            if (cryptoType.equals(SYMMETRIC_CRYPTO)) {
                SecretKey key = loadSymmetricKey();
                ICryptoSymmetricAlgorithm algo = (ICryptoSymmetricAlgorithm) cryptoService.getAlgorithm();
                result = algo.encrypt(fileData, key);
            } else {
                PublicKey key = loadPublicKey();
                ICryptoAsymmetricAlgorithm algo = (ICryptoAsymmetricAlgorithm) cryptoService.getAlgorithm();
                result = algo.encrypt(fileData, key);
            }

            //TODO - пересмотреть формирование расширений у файлов
            fileController.write(
                    result,
                    BasePath.CRYPTO_FILE_DIR,
                    RandomNamer.generateRandomText(8),
                    FileExtension.EXTENSION_ENCRYPTED_FILE.getExtension()
            );

            MainFrame.showInformation("Шифрование выполнено успешно");

        } catch (Exception e) {
            MainFrame.showError("Ошибка шифрования: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void decrypt() {
        if (view.isPathFiledEmpty()) {
            MainFrame.showError("Не указан путь к файлу");
            return;
        }
        if (view.isKeyPathEmpty()) {
            MainFrame.showError("Не указан путь к ключу");
            return;
        }

        try {
            setupCryptoService();

            Data fileData = fileController.read(view.getFilePathField().getText());
            String cryptoType = getSelectedCryptoType();

            Data result;
            if (cryptoType.equals(SYMMETRIC_CRYPTO)) {
                SecretKey key = loadSymmetricKey();
                ICryptoSymmetricAlgorithm algo = (ICryptoSymmetricAlgorithm) cryptoService.getAlgorithm();
                result = algo.decrypt(fileData, key);
            } else {
                PrivateKey key = loadPrivateKey();
                ICryptoAsymmetricAlgorithm algo = (ICryptoAsymmetricAlgorithm) cryptoService.getAlgorithm();
                result = algo.decrypt(fileData, key);
            }

            //TODO - пересмотреть формирование расширений у файлов
            fileController.write(
                    result,
                    BasePath.CRYPTO_FILE_DIR,
                    RandomNamer.generateRandomText(8),
                    ""
            );

            MainFrame.showInformation("Расшифровка выполнена успешно");

        } catch (Exception e) {
            MainFrame.showError("Ошибка расшифровки: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //endregion

    private SecretKey loadSymmetricKey() throws Exception {
        Data keyData = fileController.read(view.getKeyPathField().getText());
        return pemFileReader.fromPemSecret(keyData.getData());
    }

    private PublicKey loadPublicKey() throws Exception {
        Data keyData = fileController.read(view.getKeyPathField().getText());
        return pemFileReader.fromPemPublic(keyData.getData());
    }

    private PrivateKey loadPrivateKey() throws Exception {
        Data keyData = fileController.read(view.getKeyPathField().getText());
        return pemFileReader.fromPemPrivate(keyData.getData());
    }

    //region Transform
    private String getTransform() {
        return SYMMETRIC_CRYPTO.equals(getSelectedCryptoType())
                ? buildSymmetricTransform()
                : buildAsymmetricTransform();
    }

    private String buildSymmetricTransform() {
        String[] transformComponents = {
                view.getAlgorithmCombo().getSelectedItem().toString(),
                view.getModeCombo().getSelectedItem().toString(),
                view.getPaddingCombo().getSelectedItem().toString()
        };
        return String.join("/", transformComponents);
    }

    private String buildAsymmetricTransform() {
        String[] transformComponents = {
                view.getAlgorithmCombo().getSelectedItem().toString(),
                "ECB",
                "PKCS1Padding"
        };
        return String.join("/", transformComponents);
    }
    //endregion

    //region SelectAlgorithm
    private void updateAlgorithms() {
        String cryptoType = getSelectedCryptoType();
        updateAlgorithmComboBox(cryptoType);
        updateModeAndPaddingVisibility(cryptoType);
    }

    private void updateAlgorithmComboBox(String cryptoType) {
        String[] algorithms = SYMMETRIC_CRYPTO.equals(cryptoType)
                ? view.getSYMMETRIC_ALGORITHMS()
                : view.getASYMMETRIC_ALGORITHMS();

        view.getAlgorithmCombo().setModel(new DefaultComboBoxModel<>(algorithms));
    }

    private void updateModeAndPaddingVisibility(String cryptoType) {
        boolean isSymmetric = SYMMETRIC_CRYPTO.equals(cryptoType);

        view.getModeCombo().setEnabled(isSymmetric);
        view.getPaddingCombo().setEnabled(isSymmetric);
    }

    private String getSelectedCryptoType() {
        return view.getCryptoTypeCombo().getSelectedItem().toString();
    }
    //endregion

    //region Open Dialog
    private void openFileSelect() {
        PathDialog pathDialog = new PathDialog();

        String path = pathDialog.openFileDialog();

        view.getFilePathField().setText(path);
    }

    private void openFileSelect(String flag) {
        PathDialog pathDialog = new PathDialog();

        String path = pathDialog.openFileDialog(flag,BasePath.KEYS_FILE_DIR.getPath().toString());

        view.getKeyPathField().setText(path);
    }

    private void openKeyMaster() {
        KeyMasterDialog keyMasterDialog = new KeyMasterDialog(null);
        KeyMasterDialogController keyMasterDialogController = new KeyMasterDialogController(keyMasterDialog,fileModel);
        keyMasterDialog.show();
    }
    //endregion
}