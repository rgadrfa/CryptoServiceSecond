package view.dialog;

import model.file.enums.BasePath;
import view.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class PathDialog {
    private static final String FILE_CHOOSER_TITLE = "Выберите файл для шифрования";
    private JFileChooser fileChooser;

    public String openFileDialog() {
        return openFileDialog(null);
    }

    public String openFileDialog(String flag) {
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(FILE_CHOOSER_TITLE);

        if (flag != null) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Файлы с расширением *." + flag, flag);
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
        }

        if (fileChooser.showOpenDialog(MainFrame.getInstance().mainFrame) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }
}
