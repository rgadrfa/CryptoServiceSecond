package model.key;

import model.key.interfaces.IAsymmetricKey;
import model.key.interfaces.ICryptoKey;
import model.key.interfaces.IKeyService;
import model.key.interfaces.ISymmetricKey;

import javax.crypto.SecretKey;
import java.security.KeyPair;

public class KeyService implements IKeyService {

    private final ICryptoKey cryptoController;

    public KeyService(ICryptoKey cryptoController) {
        this.cryptoController = cryptoController;
    }

    @Override
    public KeyPair createPair() {
        return ((IAsymmetricKey) cryptoController).createPair();
    }

    @Override
    public SecretKey createSecret() {
        return ((ISymmetricKey) cryptoController).createSecret();
    }
}
