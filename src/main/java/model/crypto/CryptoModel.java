package model.crypto;

import model.crypto.interfaces.ICryptoAlgorithm;
import model.file.Data;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

public class CryptoModel<T extends ICryptoAlgorithm> {
    private final T algorithm;

    public CryptoModel(T algorithm) {
        this.algorithm = algorithm;
    }

    public T getAlgorithm(){
        return algorithm;
    }
}
