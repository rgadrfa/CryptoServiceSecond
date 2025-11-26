package model.crypto;

import model.crypto.interfaces.ICryptoAsymmetricAlgorithm;
import model.crypto.interfaces.ICryptoSymmetricAlgorithm;

public class CryptoFactory {
    public static CryptoModel<ICryptoSymmetricAlgorithm> create(ICryptoSymmetricAlgorithm algorithm){
        return new CryptoModel<>(algorithm);
    }

    public static CryptoModel<ICryptoAsymmetricAlgorithm> create(ICryptoAsymmetricAlgorithm algorithm){
        return new CryptoModel<>(algorithm);
    }
}
