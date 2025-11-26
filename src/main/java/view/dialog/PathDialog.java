package view.dialog;

import view.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class PathDialog {
    private static final String FILE_CHOOSER_TITLE = "Выберите файл для шифрования";
    private JFileChooser fileChooser;
    public String openFileDialog() {
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(FILE_CHOOSER_TITLE);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Текстовые файлы (*.txt)", "txt");

        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showOpenDialog(MainFrame.getInstance().mainFrame) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return "";
    }
}
