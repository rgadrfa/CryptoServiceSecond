package app;

import model.key.KeyFactory;
import model.key.controllers.SymmetricKeyController;
import model.key.key_store.KeyStoreModel;
import model.key.key_store.controllers.AuthKeyStoreController;
import model.key.key_store.controllers.OperationKeyStoreController;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Enumeration;

public class Program {
    public static void main(String[] args) throws Exception {
        // 1. Создаем новый KeyStore
        var auth = new AuthKeyStoreController();
        auth.authenticate("test.p12", "password");

        // 2. Создаем операционный контроллер
        var ops = new OperationKeyStoreController(auth);

        // 3. Создаем простой ключ
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey key = keyGen.generateKey();

        // 4. Сохраняем ключ
        ops.saveKey("testKey5", key, "keyPass", "password");

        // 5. Проверяем алиасы
        Enumeration<String> aliases = ops.listAliases();
        System.out.println("Алиасы в KeyStore:");
        while (aliases.hasMoreElements()) {
            System.out.println(" - " + aliases.nextElement());
        }
    }
}
