package model.key;

import model.key.interfaces.ICryptoKey;

public class KeyService<K extends ICryptoKey> {

    private final K cryptoController;

    public KeyService(K cryptoController) {
        this.cryptoController = cryptoController;
    }

    public K getCryptoController(){
        return cryptoController;
    }
}
