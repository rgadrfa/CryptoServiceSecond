package model.key.controllers;

import model.key.interfaces.IAsymmetricKey;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class AsymmetricKeyController implements IAsymmetricKey {
    private final KeyPairGenerator keyGenerator;

    public AsymmetricKeyController(String transform,int keySize) throws NoSuchAlgorithmException {
        this.keyGenerator = KeyPairGenerator.getInstance(transform);
        this.keyGenerator.initialize(keySize);
    }
    @Override
    public KeyPair createPair() {
        return keyGenerator.generateKeyPair();
    }
}
