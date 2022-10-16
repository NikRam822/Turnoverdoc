package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${upload.path}")
    private String uploadPath="src/main/resources/Orders";


    public String uploadFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                Path copyLocation = Paths
                        .get(uploadPath+ File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
                Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
                return "You have successfully downloaded " + name;
            } catch (Exception e) {
                return "You were unable to download " + name + " => " + e.getMessage();
            }
        } else {
            return "You were unable to download " + name + " because the file is empty.";
        }

    }
}