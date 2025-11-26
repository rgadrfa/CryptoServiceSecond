package controller;

import model.file.FileModel;
import model.file.enums.BasePath;
import view.dialog.KeyMasterDialog;

import java.io.IOException;

public class KeyMasterDialogController {

    private final KeyMasterDialog view;
    private final FileModel fileModel;

    public KeyMasterDialogController(KeyMasterDialog keyMasterDialog, FileModel fileModel){
        this.view = keyMasterDialog;
        this.fileModel = fileModel;
        setupList();
    }

    private void setupList(){
        //view.getKeysList().setListData();
    }
}
