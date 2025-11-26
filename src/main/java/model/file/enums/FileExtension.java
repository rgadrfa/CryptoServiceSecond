package model.file.enums;

public enum FileExtension {
    EXTENSION_KEY_FILE(".p12"),
    EXTENSION_ENCRYPTED_FILE(".encrypted");

    private final String extension;
    FileExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension(){
        return extension;
    }
}
