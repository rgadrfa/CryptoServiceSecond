package model.file;

import model.file.controllers.FileController;
import model.file.controllers.PathController;
import model.file.interfaces.IFileController;
import model.file.interfaces.IPathController;
import model.file.interfaces.IPemFileReader;
import model.file.pem_decoder.PemFileReaderController;

import java.io.IOException;

public class FileModel {
    private final IFileController fileController;
    private final IPathController pathController;
    private final IPemFileReader pemFileReader;

    public FileModel() throws IOException {
        this.pemFileReader = new PemFileReaderController();
        this.pathController = new PathController();
        this.fileController = new FileController();
    }

    public IFileController getFileController(){
        return fileController;
    }

    public IPemFileReader getPemFileReader() {
        return pemFileReader;
    }
}
