package model.file.enums;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum BasePath {
    // Базовые пути
    USER_BASE_DIR(Paths.get(System.getProperty("user.home"))),
    BASE_APP_DIR(USER_BASE_DIR.path.resolve(".crypto-service")),

    CRYPTO_FILE_DIR(BASE_APP_DIR.path.resolve("crypto-files")),
    KEYS_FILE_DIR(BASE_APP_DIR.path.resolve("keys"));

    private final Path path;
    BasePath(Path path) {
        this.path = path;
    }

    public String toString(){
        return path.toString();
    }

    public Path getPath(){
        return path;
    }
}
