package com.turnoverdoc.turnover.services.impl.file_service;

import com.turnoverdoc.turnover.services.impl.file_service.files_behavior_impl.*;

public enum DocumentFiles {

    P_45(new P45()),
    P_60(new P60()),
    P_80(new P80()),
    PASSPORT(new Passport()),
    CONTRACT(new Contract());


    public FilePathManager implementation;


    DocumentFiles(FilePathManager implementation) {

        this.implementation = implementation;
    }
}
