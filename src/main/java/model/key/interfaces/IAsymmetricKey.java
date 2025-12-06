package model.key.interfaces;

import java.security.KeyPair;

public interface IAsymmetricKey extends ICryptoKey{
    KeyPair createPair();
}
