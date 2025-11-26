package model.key.controllers;

import model.key.interfaces.ICryptoSymmetricKey;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class SymmetricKeyController implements ICryptoSymmetricKey {
    private final KeyGenerator keyGenerator;

    public SymmetricKeyController(String transform,int keySize) throws NoSuchAlgorithmException {
        this.keyGenerator = KeyGenerator.getInstance(transform);
        this.keyGenerator.init(keySize);
    }

    @Override
    public SecretKey create() {
        return keyGenerator.generateKey();
    }
}
