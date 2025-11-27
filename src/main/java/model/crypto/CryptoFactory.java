package model.crypto;

import model.crypto.interfaces.ICryptoAsymmetricAlgorithm;
import model.crypto.interfaces.ICryptoSymmetricAlgorithm;

public class CryptoFactory {
    public static CryptoService<ICryptoSymmetricAlgorithm> create(ICryptoSymmetricAlgorithm algorithm){
        return new CryptoService<>(algorithm);
    }

    public static CryptoService<ICryptoAsymmetricAlgorithm> create(ICryptoAsymmetricAlgorithm algorithm){
        return new CryptoService<>(algorithm);
    }
}
