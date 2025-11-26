package model.key.key_store.interfaces;

import javax.crypto.SecretKey;
import java.security.*;
import java.util.Enumeration;
import java.util.List;

public interface IOperationKeyStore {
    // Управление ключами
    void saveKey(String alias, SecretKey key, String keyPassword, String keyStorePassword) throws KeyStoreException;
    void saveKey(String alias, PrivateKey key, String keyPassword, String keyStorePassword) throws KeyStoreException;
    void saveKey(String alias, PublicKey key, String keyPassword, String keyStorePassword) throws KeyStoreException;
    Key loadKey(String alias, String keyPassword) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException;
    void deleteKey(String alias, String keyStorePassword) throws KeyStoreException;

    // Информация
    Enumeration<String> listAliases() throws KeyStoreException;
    KeyStore.Entry getKeyEntry(String alias, String keyPassword) throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException;
}
