package model.file.controllers;

import model.file.enums.BasePath;
import model.file.interfaces.IPathController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathController implements IPathController {

    public PathController() throws IOException {
        initDir(
                BasePath.BASE_APP_DIR,
                BasePath.CRYPTO_FILE_DIR,
                BasePath.KEYS_FILE_DIR
        );
    }

    private void initDir(BasePath... paths) throws IOException  {
        for (BasePath path : paths){
            if (!Files.exists(path.getPath())){
                Files.createDirectory(path.getPath());
            }
        }
    }
}
