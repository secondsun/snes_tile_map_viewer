package dev.secondsun.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class PickerController {
    @FXML
    AnchorPane anchorPane;

    @FXML
    TreeView tree;

    @FXML
    public void initialize() {
        tree.setRoot(new SimpleFileTreeItem(new File("/home/summerspittman/Projects/SNES_demoscene/libSFX/examples") {
            @Override
            public String toString() {
                return super.toString().split("/home/summerspittman/Projects/SNES_demoscene/libSFX/")[1];
            }
        }));

    }
}
