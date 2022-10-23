package com.turnoverdoc.turnover.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(MultipartFile file);

    void setDirName(String fileName);


}
