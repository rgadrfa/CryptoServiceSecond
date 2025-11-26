package model.file;

import java.io.IOException;

public class FileFactory {
    public static FileModel create() throws IOException {
        return new FileModel();
    }
}
