package com.turnoverdoc.turnover.services.impl.file_service;

import com.turnoverdoc.turnover.model.Order;

public interface FilePathManager {

   void setFilePath(Order order, String filePath);
}
