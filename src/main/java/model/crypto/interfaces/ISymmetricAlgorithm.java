package model.crypto.interfaces;

import model.file.Data;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;

public interface ISymmetricAlgorithm extends ICryptoAlgorithm {
    Data encrypt(Data data, SecretKey key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException;
    Data decrypt(Data data, SecretKey key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException;
}
