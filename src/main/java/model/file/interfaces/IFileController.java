package model.file.interfaces;

import model.file.Data;
import model.file.enums.BasePath;
import model.file.enums.FileExtension;

import java.io.IOException;

public interface IFileController {
    void write(Data data,BasePath basePath, String name, FileExtension extension) throws IOException;
    void write(Data data, String name) throws IOException;
    Data read(String name, BasePath path) throws IOException;
    Data read(String basePath) throws IOException;
    void remove(String name, BasePath path) throws IOException;
    String[] getFiles(BasePath path) throws IOException;
}
