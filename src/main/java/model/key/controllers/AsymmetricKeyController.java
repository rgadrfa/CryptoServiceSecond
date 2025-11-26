package model.key.controllers;

import model.key.interfaces.ICryptoAsymmetricKey;

import javax.crypto.KeyGenerator;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class AsymmetricKeyController implements ICryptoAsymmetricKey {
    private final KeyPairGenerator keyGenerator;

    public AsymmetricKeyController(String transform,int keySize) throws NoSuchAlgorithmException {
        this.keyGenerator = KeyPairGenerator.getInstance(transform);
        this.keyGenerator.initialize(keySize);
    }
    @Override
    public KeyPair create() {
        return keyGenerator.generateKeyPair();
    }
}
