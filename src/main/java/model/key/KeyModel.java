package model.key;

import model.key.interfaces.ICryptoKey;

public class KeyModel<K extends ICryptoKey> {

    private final K cryptoController;

    public KeyModel(K cryptoController) {
        this.cryptoController = cryptoController;
    }

    public K getCryptoController(){
        return cryptoController;
    }
}
