package com.turnoverdoc.turnover.services.impl.file_service;

import com.turnoverdoc.turnover.model.Order;
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


    @Value("${upload.path}")
    private String uploadPath;


    @Override
    public boolean saveFiles(MultipartFile[] files, Order order, String dirName) {

        for (MultipartFile file : files) {

            if (file != null) {
                String currentFilePath = uploadFile(file, dirName);
                if (currentFilePath == null) {
                    LOGGER.warn("File " + file.getName() + " is not saved. Current file path is null.");
                    return false;
                }
                setPathFile(order, file.getName(), currentFilePath);
            }
        }
        return true;
    }

    private String uploadFile(MultipartFile file, String dirName) {
        String newDirPath = createFolder(dirName);
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

    private String createFolder(String dirName) {

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

    private void setPathFile(Order order, String fileName, String filePath) {

        try {

            FilePathManager filePathManager1 = DocumentFiles.valueOf(fileName).implementation;

            filePathManager1.setFilePath(order, filePath);
        } catch (Exception exception) {
            LOGGER.warn("File " + fileName + " incorrect set in db. Error: " + exception);

        }

    }


    public File getFileDoc(String dirPath, String fileName) {
        String pathToDir = uploadPath + "\\" + dirPath;
        File[] folderEntries = new File(pathToDir).listFiles();

        if (folderEntries != null) {
            for (File entry : folderEntries) {
                if (entry.toPath().toString().contains(pathToDir + "\\" + fileName)) {
                    return entry;
                }
            }
        }
        // TODO: Make exception for non-exist directory
        LOGGER.error("File is not exists: " + pathToDir + "\\" + fileName);
        return null;
    }


}

