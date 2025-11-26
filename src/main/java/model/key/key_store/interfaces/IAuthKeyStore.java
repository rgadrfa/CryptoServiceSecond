package model.key.key_store.interfaces;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public interface IAuthKeyStore {
    void authenticate(String name, String password) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException;
    void createNewKeyStore(String name,String password) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException;
}
