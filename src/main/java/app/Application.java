package app;

import controller.MainPanelController;
import model.file.FileFactory;
import model.file.FileModel;
import view.MainFrame;
import view.panel.MainPanel;

import javax.swing.*;
import java.io.IOException;

public class Application {
    private static final String WINDOW_TITLE = "Crypto Service";
    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 370;

    private FileModel fileModel;
    private MainPanel mainPanel;
    private MainPanelController mainPanelController;

    public Application() {
        initializeApplication();
    }

    public void run() {
        showMainWindow();
    }

    private void initializeApplication() {
        initModel();
        initPanel();
        initController();
    }

    private void initController() {
        mainPanelController = new MainPanelController(mainPanel, fileModel);
    }

    private void initPanel() {
        mainPanel = new MainPanel();
    }

    private void showMainWindow() {
        MainFrame frame = MainFrame.getInstance();
        configureMainFrame(frame);
        frame.showWindow();
        frame.build();
    }

    private void configureMainFrame(MainFrame frame) {
        frame.setWindowName(WINDOW_TITLE);
        frame.setWindowSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setPanel(mainPanel.getPanel());
    }

    private void initModel() {
        try {
            fileModel = FileFactory.create();
        } catch (IOException e) {
            handleFileModelInitializationError();
        }
    }

    private void handleFileModelInitializationError() {
        JOptionPane.showMessageDialog(
                null,
                "Не удалось создать директорию для работы приложения",
                "Ошибка инициализации",
                JOptionPane.ERROR_MESSAGE
        );
    }
}