package app;

import controller.AuthDialogController;
import controller.KeyMasterDialogController;
import controller.MainPanelController;
import model.file.FileFactory;
import model.file.FileModel;
import view.MainFrame;
import view.dialog.AuthDialog;
import view.dialog.KeyMasterDialog;
import view.panel.MainPanel;

import javax.swing.*;
import java.io.IOException;

public class Application {
    private FileModel fileModel;
    private MainPanel mainPanel;

    private MainPanelController mainPanelController;

    public Application() {
        initModel();
        initPanel();
        initController();
    }

    public void run() {
        initKeyStore();
        initWindow();
    }

    private void initKeyStore() {

        AuthDialog authDialog = new AuthDialog(null);
        AuthDialogController authDialogController = new AuthDialogController(authDialog,fileModel);
        authDialog.showDialog();
    }

    private void initController() {
        mainPanelController = new MainPanelController(mainPanel, fileModel);
    }

    private void initPanel() {
        mainPanel = new MainPanel();
    }

    //region Window Init
    private void initWindow() {
        MainFrame frame = MainFrame.getInstance();
        frame.setWindowName("Crypto Service");
        frame.setWindowSize(840,340);
        frame.setPanel(mainPanel.getPanel());
        frame.showWindow();
        frame.build();
    }
    //endregion

    private void initModel() {

        //TODO-пересмотреть создание исключения
        try {
            fileModel = FileFactory.create();
        } catch (IOException e) {
            JOptionPane.showInputDialog(MainFrame.getInstance(),
                    "Не удалось создать директорию");
        }
    }

}
