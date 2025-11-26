package model.key.key_store.controllers;

import model.key.KeyFactory;
import model.key.key_store.interfaces.IOperationKeyStore;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Enumeration;

public class OperationKeyStoreController implements IOperationKeyStore {

    private final AuthKeyStoreController authKeyStoreController;
    private final KeyStore keyStore;


    public OperationKeyStoreController(AuthKeyStoreController authKeyStoreController) {
        this.authKeyStoreController = authKeyStoreController;
        this.keyStore = authKeyStoreController.getKeyStore();
    }

    private void saveKeyStore(char[] keyStorePassword) throws KeyStoreException {
        try (FileOutputStream fos = new FileOutputStream(authKeyStoreController.getKEY_STORE_PATH())) {
            keyStore.store(fos, keyStorePassword);
            fos.flush();

            // 🔄 ПЕРЕЗАГРУЗКА KeyStore С ДИСКА
            reloadKeyStore(keyStorePassword);

        } catch (Exception e) {
            throw new KeyStoreException(e.getMessage());
        }
    }

    // 🔄 ДОБАВЬ ЭТОТ МЕТОД
    private void reloadKeyStore(char[] keyStorePassword) throws KeyStoreException {
        try (FileInputStream fis = new FileInputStream(authKeyStoreController.getKEY_STORE_PATH())) {
            keyStore.load(fis, keyStorePassword);
        } catch (Exception e) {
            throw new KeyStoreException("Failed to reload KeyStore: " + e.getMessage());
        }
    }

    @Override
    public void saveKey(String alias, SecretKey secretKey, String keyPassword, String keyStorePassword)
            throws KeyStoreException {
        char[] keyPasswordChars = keyPassword.toCharArray();
        char[] storePasswordChars = keyStorePassword.toCharArray();
        try {
            KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(secretKey);
            KeyStore.ProtectionParameter protection = new KeyStore.PasswordProtection(keyPasswordChars);
            keyStore.setEntry(alias, entry, protection);
            saveKeyStore(storePasswordChars);
        } finally {
            Arrays.fill(keyPasswordChars, '\0');
            Arrays.fill(storePasswordChars, '\0');
        }
    }

    @Override
    public void saveKey(String alias, PrivateKey privateKey, String keyPassword, String keyStorePassword)
            throws KeyStoreException {
        char[] keyPasswordChars = keyPassword.toCharArray();
        char[] storePasswordChars = keyStorePassword.toCharArray();
        try {
            KeyStore.PrivateKeyEntry entry = new KeyStore.PrivateKeyEntry(
                    privateKey,
                    new Certificate[0]
            );
            KeyStore.ProtectionParameter protection = new KeyStore.PasswordProtection(keyPasswordChars);
            keyStore.setEntry(alias, entry, protection);
            saveKeyStore(storePasswordChars);
        } finally {
            Arrays.fill(keyPasswordChars, '\0');
            Arrays.fill(storePasswordChars, '\0');
        }
    }

    @Override
    public void saveKey(String alias, PublicKey publicKey, String keyPassword, String keyStorePassword)
            throws KeyStoreException {
        char[] keyPasswordChars = keyPassword.toCharArray();
        char[] storePasswordChars = keyStorePassword.toCharArray();
        try {
            SecretKey publicKeyWrapper = new SecretKeySpec(
                    publicKey.getEncoded(),
                    publicKey.getAlgorithm()
            );
            KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(publicKeyWrapper);
            KeyStore.ProtectionParameter protection = new KeyStore.PasswordProtection(keyPasswordChars);
            keyStore.setEntry(alias, entry, protection);
            saveKeyStore(storePasswordChars);
        } finally {
            Arrays.fill(keyPasswordChars, '\0');
            Arrays.fill(storePasswordChars, '\0');
        }
    }

    @Override
    public Key loadKey(String alias, String keyPassword)
            throws KeyStoreException,
            UnrecoverableKeyException,
            NoSuchAlgorithmException {
        char[] passwordChars = keyPassword.toCharArray();
        try {
            return keyStore.getKey(alias, passwordChars);
        } finally {
            Arrays.fill(passwordChars, '\0');
        }
    }

    @Override
    public void deleteKey(String alias, String keyStorePassword)
            throws KeyStoreException {
        char[] storePasswordChars = keyStorePassword.toCharArray();
        try {
            if (!keyStore.containsAlias(alias)) {
                throw new KeyStoreException("Key not found: " + alias);
            }
            keyStore.deleteEntry(alias);
            saveKeyStore(storePasswordChars);
        } finally {
            Arrays.fill(storePasswordChars, '\0');
        }
    }

    @Override
    public Enumeration<String> listAliases() throws KeyStoreException {
        return keyStore.aliases();
    }

    @Override
    public KeyStore.Entry getKeyEntry(String alias, String keyPassword)
            throws KeyStoreException,
            UnrecoverableEntryException,
            NoSuchAlgorithmException {
        char[] passwordChars = keyPassword.toCharArray();
        try {
            KeyStore.ProtectionParameter protection = new KeyStore.PasswordProtection(passwordChars);
            return keyStore.getEntry(alias, protection);
        } finally {
            Arrays.fill(passwordChars, '\0');
        }
    }

    public Key loadKey(String alias, char[] keyPassword)
            throws KeyStoreException,
            UnrecoverableKeyException,
            NoSuchAlgorithmException {
        try {
            return keyStore.getKey(alias, keyPassword);
        } finally {
            Arrays.fill(keyPassword, '\0');
        }
    }
}