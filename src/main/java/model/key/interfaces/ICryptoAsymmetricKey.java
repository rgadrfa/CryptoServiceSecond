package model.key.interfaces;

import java.security.Key;
import java.security.KeyPair;

public interface ICryptoAsymmetricKey extends ICryptoKey{
    KeyPair create();
}
