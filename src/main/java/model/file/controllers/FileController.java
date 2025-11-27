package model.file.controllers;

import model.file.Data;
import model.file.enums.BasePath;
import model.file.enums.FileExtension;
import model.file.interfaces.IFileController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileController implements IFileController {

    public FileController() { }

    @Override
    public void write(Data data, BasePath basePath, String name, String extension) throws IOException {
        Path path = buildPath(name, basePath, extension);
        Files.write(path, data.getData());
    }

    @Override
    public void write(Data data, String name) throws IOException {
        Path path = Paths.get(name);
        Files.write(path, data.getData());
    }

    @Override
    public Data read(String name, BasePath basePath) throws IOException {
        Path path = buildPath(name, basePath);
        return new Data(Files.readAllBytes(path));
    }

    public Data read(String basePath) throws IOException {
        Path path = Paths.get(basePath);
        return new Data(Files.readAllBytes(path));
    }

    @Override
    public void remove(String name, BasePath basePath) throws IOException {
        Path path = buildPath(name, basePath);
        Files.deleteIfExists(path);
    }

    public String[] getFiles(BasePath path) throws IOException {
        return Files
                .list(path.getPath())
                .map(Path::getFileName)
                .map(Path::toString)
                .toArray(String[]::new);
    }

    private Path buildPath(String name,BasePath basePath, String extension) {
        return basePath
                .getPath()
                .resolve(name + extension);
    }

    private Path buildPath(String name, BasePath basePath) {
        return basePath.getPath().resolve(name);
    }
}
