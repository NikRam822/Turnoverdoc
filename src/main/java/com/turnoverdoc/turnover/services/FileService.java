package com.turnoverdoc.turnover.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    boolean uploadFile(MultipartFile file);
}
