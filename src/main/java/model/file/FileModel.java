package model.file;

import model.file.controllers.FileController;
import model.file.controllers.PathController;
import model.file.interfaces.IFileController;
import model.file.interfaces.IPathController;

import java.io.IOException;

public class FileModel {
    private final IFileController fileController;
    private final IPathController pathController;

    public FileModel() throws IOException {
        this.pathController = new PathController();
        this.fileController = new FileController();
    }

    public IFileController getFileController(){
        return fileController;
    }
}
