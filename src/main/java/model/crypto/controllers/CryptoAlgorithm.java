package model.crypto.controllers;

import model.file.Data;

import javax.crypto.*;
import java.security.*;

public abstract class CryptoAlgorithm {
    private final Cipher cipher;

    protected CryptoAlgorithm(String transformation)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance(transformation);
    }

    public Cipher getCipher() {
        return cipher;
    }

    public <K extends Key> Data decode(Data data, K key, int mode)
            throws InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        cipher.init(mode, key);
        return new Data(cipher.doFinal(data.getData()));
    }
}
