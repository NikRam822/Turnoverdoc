package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.model.Order;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {

    boolean saveFiles(MultipartFile[] files, Order order,String dirName);
    File getFileDoc(String filePath, String FileName);
}
