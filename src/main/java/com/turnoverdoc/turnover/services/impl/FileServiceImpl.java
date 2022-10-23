package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final Logger LOGGER = log;

    String dirName;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void setDirName(String fileName) {
        this.dirName = fileName;
    }


    public String uploadFile(MultipartFile file) {
        String newDirPath = createFolder();
        String name = file.getName();
        if (!file.isEmpty()) {
            try {
                Path copyLocation = Paths
                        .get(newDirPath + File.separator + name + getFileExtension(file.getOriginalFilename()));
                Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("You have successfully downloaded " + name);
                return String.valueOf(copyLocation);
            } catch (Exception e) {
                LOGGER.error("You were unable to download " + name + " => " + e.getMessage());
                return null;
            }
        } else {
            LOGGER.error("You were unable to download " + name + " because the file is empty.");
            return null;
        }

    }

    private String createFolder() {

        Path path = Paths.get(this.uploadPath + "\\" + dirName);

        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
            LOGGER.info("New Directory created: " + path);
        } else {
            LOGGER.warn("Directory already exists");

        }
        return String.valueOf(path);
    }

    private String getFileExtension(String fileName) {
        int index = fileName.indexOf('.');
        return index == -1 ? null : fileName.substring(index);
    }
}

