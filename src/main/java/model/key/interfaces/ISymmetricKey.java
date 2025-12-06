package model.key.interfaces;

import javax.crypto.SecretKey;

public interface ISymmetricKey extends ICryptoKey {
    SecretKey createSecret();
}
