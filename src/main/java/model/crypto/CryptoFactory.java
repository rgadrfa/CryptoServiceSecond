package model.crypto;

import model.crypto.interfaces.IAsymmetricAlgorithm;
import model.crypto.interfaces.ISymmetricAlgorithm;

public class CryptoFactory {
    public static CryptoService<ISymmetricAlgorithm> create(ISymmetricAlgorithm algorithm){
        return new CryptoService<>(algorithm);
    }

    public static CryptoService<IAsymmetricAlgorithm> create(IAsymmetricAlgorithm algorithm){
        return new CryptoService<>(algorithm);
    }
}
