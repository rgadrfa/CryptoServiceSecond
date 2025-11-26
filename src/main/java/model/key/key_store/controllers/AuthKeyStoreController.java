package model.key.key_store.controllers;

import model.file.enums.BasePath;
import model.key.key_store.interfaces.IAuthKeyStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;

public class AuthKeyStoreController implements IAuthKeyStore {
    private final String KEY_STORE_TYPE = KeyStore.getDefaultType();
    private final String KEY_STORE_DIR_PATH = BasePath.KEYS_FILE_DIR.toString();

    private String KEY_STORE_PATH;
    private KeyStore keyStore;

    public AuthKeyStoreController() { }

    @Override
    public void authenticate(String name, String password)
            throws KeyStoreException,
            CertificateException,
            IOException,
            NoSuchAlgorithmException {

        char[] passwordChars = password.toCharArray();
        try {
            File file = new File(KEY_STORE_DIR_PATH, name);
            keyStore = KeyStore.getInstance(file, passwordChars);
            KEY_STORE_PATH = KEY_STORE_DIR_PATH + name;
        } finally {
            Arrays.fill(passwordChars, '\0');
        }
    }

    //TODO - обязательно указать в курсовой про хранение пароля
    @Override
    public void createNewKeyStore(String name, String password)
            throws KeyStoreException,
            CertificateException,
            IOException,
            NoSuchAlgorithmException {

        char[] passwordChars = password.toCharArray();
        try {
            keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
            keyStore.load(null, passwordChars);

            File file = new File(KEY_STORE_DIR_PATH, name);

            if (file.exists()){
                throw new FileAlreadyExistsException(file.getName());
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                keyStore.store(fos, passwordChars);
            }

            KEY_STORE_PATH = KEY_STORE_DIR_PATH + name;
        } finally {
            Arrays.fill(passwordChars, '\0');
        }
    }

    public KeyStore getKeyStore() {
        return keyStore;
    }

    public String getKEY_STORE_PATH() {
        return KEY_STORE_PATH;
    }
}
