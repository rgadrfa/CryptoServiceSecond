package controller;

import model.file.Data;
import model.file.FileModel;
import model.file.enums.BasePath;
import model.file.enums.FileExtension;
import model.file.interfaces.IFileController;
import model.file.interfaces.IPemFile;
import model.key.KeyFactory;
import model.key.controllers.AsymmetricKeyController;
import model.key.controllers.SymmetricKeyController;
import model.key.interfaces.IKeyService;
import view.MainFrame;
import view.dialog.KeyMasterDialog;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.security.KeyPair;

public class KeyMasterDialogController {
    private final KeyMasterDialog view;
    private final FileModel fileModel;

    private IFileController fileController;
    private IPemFile pemFileReader;

    private IKeyService keyService;
    private final String SYMMETRIC_CRYPTO = "Симметричный";

    public KeyMasterDialogController(KeyMasterDialog keyMasterDialog, FileModel fileModel) {
        this.view = keyMasterDialog;
        this.fileModel = fileModel;

        fileController = fileModel.getFileController();
        pemFileReader = fileModel.getPemFileReader();

        initController();
        updateAlgorithms();
    }

    private void initController() {
        view.getGenerateButton().addActionListener(_ -> generateKey());
        view.getKeyCategoryComboBox().addActionListener(_ -> updateAlgorithms());
    }

    private void generateKey() {
        String keyName = view.getKeyNameField().getText().trim();
        if (keyName.isEmpty()) {
            MainFrame.showError("Введите имя ключа");
            return;
        }

        String type = getSelectedCryptoType();
        String algorithm = view.getAlgorithmTypeComboBox().getSelectedItem().toString();
        String sizeStr = view.getKeySizeComboBox().getSelectedItem().toString();

        int keySize = Integer.parseInt(sizeStr.replaceAll("\\D+", "")); // "2048 бит" → 2048

        try {
            if (SYMMETRIC_CRYPTO.equals(type)) {
                generateSymmetricKey(algorithm, keySize, keyName);
            } else {
                generateAsymmetricKeyPair(algorithm, keySize, keyName);
            }

            MainFrame.showInformation("Ключ(и) успешно сгенерированы и сохранены!");

            view.getKeyNameField().setText("");
        } catch (Exception ex) {
            MainFrame.showError("Ошибка генерации ключа: " + ex.getMessage());
        }
    }

    private void generateSymmetricKey(String algorithm, int keySize, String baseName) throws Exception {

        SymmetricKeyController controller = new SymmetricKeyController(algorithm, keySize);
        keyService = KeyFactory.create(controller);

        SecretKey secretKey = keyService.createSecret();

        String fileName = baseName + "_" + algorithm.toLowerCase() + "_" + keySize;

        byte[] pemBytes = pemFileReader.toPem(secretKey);
        fileController.write(
                new Data(pemBytes), BasePath.KEYS_FILE_DIR,fileName,FileExtension.EXTENSION_KEY_FILE.getExtension()
        );


    }

    private void generateAsymmetricKeyPair(String algorithm, int keySize, String baseName) throws Exception {
        AsymmetricKeyController controller = new AsymmetricKeyController(algorithm, keySize);
        keyService = KeyFactory.create(controller);
        KeyPair keyPair = keyService.createPair();

        String publicKeyName = baseName + "_" + algorithm.toLowerCase() + "_public_" + keySize;
        String privateKeyName = baseName + "_" + algorithm.toLowerCase() + "_private_" + keySize;


        byte[] pemBytes = pemFileReader.toPem(keyPair.getPublic());
        fileController.write(
                new Data(pemBytes), BasePath.KEYS_FILE_DIR,publicKeyName,FileExtension.EXTENSION_KEY_FILE.getExtension()
        );

        byte[] pemBytes1 = pemFileReader.toPem(keyPair.getPrivate());
        fileController.write(
                new Data(pemBytes1), BasePath.KEYS_FILE_DIR,privateKeyName,FileExtension.EXTENSION_KEY_FILE.getExtension()
        );
    }

    private void updateAlgorithms() {
        String cryptoType = getSelectedCryptoType();
        updateAlgorithmComboBox(cryptoType);
        updateKeySizeComboBox(cryptoType);
    }

    private void updateAlgorithmComboBox(String cryptoType) {
        String[] algorithms = SYMMETRIC_CRYPTO.equals(cryptoType)
                ? view.getSYMMETRIC_ALGORITHMS()
                : view.getASYMMETRIC_ALGORITHMS();

        view.getAlgorithmTypeComboBox().setModel(new DefaultComboBoxModel<>(algorithms));
    }

    private void updateKeySizeComboBox(String cryptoType) {
        String[] sizes = SYMMETRIC_CRYPTO.equals(cryptoType)
                ? view.getSYMMETRIC_KEY_SIZES()
                : view.getASYMMETRIC_KEY_SIZES();

        view.getKeySizeComboBox().setModel(new DefaultComboBoxModel<>(sizes));
    }

    private String getSelectedCryptoType() {
        return view.getKeyCategoryComboBox().getSelectedItem().toString();
    }
}