package model.file.enums;

public enum FileExtension {
    EXTENSION_KEY_FILE(".pem"),
    EXTENSION_ENCRYPTED_FILE(".encrypted");

    private final String extension;
    FileExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension(){
        return extension;
    }

    public String getName(){
        return extension.substring(1);
    }
}
