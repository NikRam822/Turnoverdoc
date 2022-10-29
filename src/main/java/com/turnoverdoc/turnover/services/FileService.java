package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.model.Order;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {


    void setDirPath(String fileName);

    boolean saveFiles(MultipartFile[] files, Order order);

}
