package model.key;

import model.key.interfaces.IAsymmetricKey;
import model.key.interfaces.ISymmetricKey;

public class KeyFactory {
    public static KeyService create(ISymmetricKey key){
        return new KeyService(key);
    }

    public static KeyService create(IAsymmetricKey key){
        return new KeyService(key);
    }
}
