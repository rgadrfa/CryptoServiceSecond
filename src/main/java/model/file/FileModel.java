package model.file;

import model.file.controllers.FileController;
import model.file.controllers.PathController;
import model.file.interfaces.IFileController;
import model.file.interfaces.IPathController;
import model.file.interfaces.IPemFile;
import model.file.pem_decoder.PemFileController;

import java.io.IOException;

public class FileModel {
    private final IFileController fileController;
    private final IPathController pathController;
    private final IPemFile pemFileReader;

    public FileModel() throws IOException {
        this.pemFileReader = new PemFileController();
        this.pathController = new PathController();
        this.fileController = new FileController();
    }

    public IFileController getFileController(){
        return fileController;
    }

    public IPemFile getPemFileReader() {
        return pemFileReader;
    }
}
