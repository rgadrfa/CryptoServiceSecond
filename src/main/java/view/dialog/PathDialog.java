package view.dialog;

import view.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class PathDialog {
    private static final String FILE_CHOOSER_TITLE = "Выберите файл для шифрования";

    public String openFileDialog() {
        return openFileDialog(null, null);
    }

    public String openFileDialog(String extension) {
        return openFileDialog(extension, null);
    }

    public String openFileDialog(String extension, String basePath) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(FILE_CHOOSER_TITLE);

        if (extension != null) {
            fileChooser.setFileFilter(new FileNameExtensionFilter(
                    "Файлы *." + extension, extension));
        }

        if (basePath != null) {
            fileChooser.setCurrentDirectory(new File(basePath));
        }

        return fileChooser.showOpenDialog(MainFrame.getInstance().mainFrame) == JFileChooser.APPROVE_OPTION
                ? fileChooser.getSelectedFile().getAbsolutePath()
                : "";
    }
}