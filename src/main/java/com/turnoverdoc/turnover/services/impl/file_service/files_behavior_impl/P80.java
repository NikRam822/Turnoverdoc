package com.turnoverdoc.turnover.services.impl.file_service.files_behavior_impl;

import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.services.impl.file_service.FilePathManagerImpl;

public class P80 extends FilePathManagerImpl {

    @Override
    public void setFilePath(Order order, String filePath) {
        order.setP80Path(filePath);
    }
}
