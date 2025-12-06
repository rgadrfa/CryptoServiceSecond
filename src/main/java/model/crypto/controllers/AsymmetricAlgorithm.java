package model.crypto.controllers;

import model.crypto.interfaces.IAsymmetricAlgorithm;
import model.file.Data;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class AsymmetricAlgorithm extends CryptoAlgorithm implements IAsymmetricAlgorithm {

    public AsymmetricAlgorithm(String transformation)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException {
        super(transformation);
    }

    @Override
    public Data encrypt(Data data, PublicKey key)
            throws IllegalBlockSizeException,
            BadPaddingException,
            InvalidKeyException {
        return super.decode(data,key, Cipher.ENCRYPT_MODE);
    }

    @Override
    public Data decrypt(Data data, PrivateKey key)
            throws IllegalBlockSizeException,
            BadPaddingException,
            InvalidKeyException {
        return super.decode(data,key, Cipher.DECRYPT_MODE);
    }
}
