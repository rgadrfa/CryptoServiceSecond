package model.crypto.controllers;

import model.crypto.interfaces.ISymmetricAlgorithm;
import model.file.Data;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SymmetricAlgorithm extends CryptoAlgorithm implements ISymmetricAlgorithm {

    public SymmetricAlgorithm(String transformation)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException {
        super(transformation);
    }

    @Override
    public Data encrypt(Data data, SecretKey key)
            throws IllegalBlockSizeException,
            BadPaddingException,
            InvalidKeyException {
        return super.decode(data,key,Cipher.ENCRYPT_MODE);
    }

    @Override
    public Data decrypt(Data data, SecretKey key)
            throws IllegalBlockSizeException,
            BadPaddingException,
            InvalidKeyException {
        return super.decode(data,key,Cipher.DECRYPT_MODE);
    }
}
