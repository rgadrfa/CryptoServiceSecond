package model.key.interfaces;

import javax.crypto.SecretKey;

public interface ICryptoSymmetricKey extends ICryptoKey{
    SecretKey create();
}
