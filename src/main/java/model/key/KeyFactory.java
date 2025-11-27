package model.key;

import model.key.interfaces.ICryptoAsymmetricKey;
import model.key.interfaces.ICryptoSymmetricKey;

public class KeyFactory {
    public static KeyService<ICryptoSymmetricKey> create(ICryptoSymmetricKey key){
        return new KeyService<>(key);
    }

    public static KeyService<ICryptoAsymmetricKey> create(ICryptoAsymmetricKey key){
        return new KeyService<>(key);
    }
}
