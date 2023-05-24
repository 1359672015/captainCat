package com.example.captaincat.model;

import com.example.captaincat.Ui.Dialog.DialogItem;

import java.util.List;

public class UpLevelInfo {
        public UpLevelInfo(List<DialogItem> dialogItemList, boolean isEnough) {
            this.dialogItemList = dialogItemList;
            this.isEnough = isEnough;
        }
        public List<DialogItem> getDialogItemList() {
            return dialogItemList;
        }
        public boolean isEnough() {
            return isEnough;
        }
        List<DialogItem> dialogItemList;
        boolean isEnough;
    }

