package model.crypto.interfaces;

import model.file.Data;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface IAsymmetricAlgorithm extends ICryptoAlgorithm{
    Data encrypt(Data data, PublicKey key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException;
    Data decrypt(Data data, PrivateKey key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException;
}
