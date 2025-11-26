package controller;


import model.crypto.CryptoFactory;
import model.crypto.CryptoModel;
import model.crypto.controllers.AsymmetricAlgorithm;
import model.crypto.controllers.SymmetricAlgorithm;
import model.crypto.interfaces.ICryptoAsymmetricAlgorithm;
import model.crypto.interfaces.ICryptoSymmetricAlgorithm;
import model.file.Data;
import model.file.FileModel;
import model.key.KeyModel;
import model.key.interfaces.ICryptoAsymmetricKey;
import model.key.interfaces.ICryptoSymmetricKey;
import view.MainFrame;
import view.dialog.KeyMasterDialog;
import view.dialog.PathDialog;
import view.panel.MainPanel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.security.auth.kerberos.EncryptionKey;
import javax.swing.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PublicKey;

public class MainPanelController {
    private final MainPanel view;
    private final FileModel fileModel;
    private CryptoModel cryptoModel;
    private KeyModel keyModel;

    // Константы для типов шифрования
    private static final String SYMMETRIC_CRYPTO = "Симметричное";
    private static final String ASYMMETRIC_CRYPTO = "Асимметричное";

    private String PATH;

    public MainPanelController(MainPanel view, FileModel fileModel){
        this.view = view;
        this.fileModel = fileModel;

        initController();
    }

    private void initController() {
        view.getFileSelectButton().addActionListener(_ -> openFileSelect());
        view.getCryptoTypeCombo().addActionListener(_ -> updateAlgorithms());
        view.getKeyMasterButton().addActionListener(_ -> openKeyMaster());
        
        view.getEncryptButton().addActionListener(_ -> encrypt());
    }

    private void setupCryptoModel() {
        String cryptoType = getSelectedCryptoType();
        try {
            if (cryptoType.equals(SYMMETRIC_CRYPTO)) {
                cryptoModel = CryptoFactory.create(new SymmetricAlgorithm(getTransform()));
            } else {
                cryptoModel = CryptoFactory.create(new AsymmetricAlgorithm(getTransform()));
            }
        } catch (Exception e) {
            MainFrame.showInf("Ошибка: " + e.getMessage());
        }
    }

    private void encrypt() {


        if (view.isPathFiledEmpty()) {
            MainFrame.showInf("Не указан путь к файлу");
            return;
        }

        try {
            setupCryptoModel();

            Data fileData = fileModel.getFileController().read(view.getFilePathField().getText());
            String cryptoType = getSelectedCryptoType();

            if (cryptoType.equals(SYMMETRIC_CRYPTO)) {
                ICryptoSymmetricAlgorithm cryptoAlgorithm = (ICryptoSymmetricAlgorithm) cryptoModel.getAlgorithm();

                cryptoAlgorithm.encrypt(fileData,null);
            } else {
                ICryptoAsymmetricAlgorithm cryptoAlgorithm = (ICryptoAsymmetricAlgorithm) cryptoModel.getAlgorithm();
                cryptoAlgorithm.encrypt(fileData,null);
            }

            MainFrame.showInf("Шифрование выполнено успешно");

        } catch (IOException e) {
            MainFrame.showInf("Ошибка чтения данных " + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            MainFrame.showInf("Неверный размер блока ключа " + e.getMessage());
        } catch (BadPaddingException e) {
            MainFrame.showInf("Ошибка:  " + e.getMessage());
        } catch (InvalidKeyException e) {
            MainFrame.showInf("Неверный ключ " + e.getMessage());
        }
    }

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

    //region SelectAlgorithm
    private void updateAlgorithms() {
        String cryptoType = getSelectedCryptoType();
        updateAlgorithmComboBox(cryptoType);
        updateModeAndPaddingVisibility(cryptoType);
    }

    private void updateAlgorithmComboBox(String cryptoType) {
        String[] algorithms = SYMMETRIC_CRYPTO.equals(cryptoType)
                ? view.SYMMETRIC_ALGORITHMS
                : view.ASYMMETRIC_ALGORITHMS;

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
        PATH = path;
    }

    private void openKeyMaster() {
        KeyMasterDialog keyMasterDialog = new KeyMasterDialog(MainFrame.getInstance().mainFrame);
    }
    //endregion
}

