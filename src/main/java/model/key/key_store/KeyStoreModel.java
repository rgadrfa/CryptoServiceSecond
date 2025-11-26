package model.key.key_store;

import model.key.key_store.controllers.AuthKeyStoreController;
import model.key.key_store.controllers.OperationKeyStoreController;

public class KeyStoreModel {
    private final AuthKeyStoreController authKeyStoreController;
    private OperationKeyStoreController operationKeyStoreController;

    public KeyStoreModel() {
        this.authKeyStoreController = new AuthKeyStoreController();
    }

    public AuthKeyStoreController getAuthKeyStoreController() {
        return authKeyStoreController;
    }

    public OperationKeyStoreController getOperationKeyStoreController() {
        return operationKeyStoreController;
    }

    public void setOperationKeyStoreController(OperationKeyStoreController operationKeyStoreController){
        this.operationKeyStoreController = operationKeyStoreController;
    }
}
