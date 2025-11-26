package controller;

import model.file.FileModel;
import model.file.enums.BasePath;
import view.dialog.AuthDialog;

import javax.swing.*;
import java.io.IOException;

public class AuthDialogController {
    private final AuthDialog view;
    private final FileModel fileModel;

    public
    AuthDialogController(AuthDialog authDialog,FileModel fileModel) {
        this.view = authDialog;
        this.fileModel = fileModel;

        initController();
    }

    private void initController() {
        try {
            view.getKeyStoreComboBox().setModel(
                    new DefaultComboBoxModel<>(fileModel.getFileController().getFiles(BasePath.KEYS_FILE_DIR))
            );
        } catch (IOException e) {
            System.exit(0);
        }

    }
}
