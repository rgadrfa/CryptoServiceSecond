package model.crypto;

import model.crypto.interfaces.ICryptoAlgorithm;

public class CryptoService<T extends ICryptoAlgorithm> {
    private final T algorithm;

    public CryptoService(T algorithm) {
        this.algorithm = algorithm;
    }

    public T getAlgorithm(){
        return algorithm;
    }
}
