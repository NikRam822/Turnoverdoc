package com.turnoverdoc.turnover.services.impl.file_service;

import com.turnoverdoc.turnover.services.impl.file_service.files_behavior_impl.*;

public enum Files {

    P_45("p45", new P45()),
    P_60("p60", new P60()),
    P_80("p80", new P80()),
    PASSPORT("passport", new Passport()),
    CONTRACT("contract", new Contract());


    public String fileName;

    public FilePathManager implementation;


    Files(String fileName, FilePathManager implementation) {
        this.fileName = fileName;
        this.implementation = implementation;
    }
    }
