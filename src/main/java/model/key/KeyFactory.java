package model.key;

import model.key.interfaces.ICryptoAsymmetricKey;
import model.key.interfaces.ICryptoSymmetricKey;

public class KeyFactory {
    public static KeyModel<ICryptoSymmetricKey> create(ICryptoSymmetricKey key){
        return new KeyModel<>(key);
    }

    public static KeyModel<ICryptoAsymmetricKey> create(ICryptoAsymmetricKey key){
        return new KeyModel<>(key);
    }
}
