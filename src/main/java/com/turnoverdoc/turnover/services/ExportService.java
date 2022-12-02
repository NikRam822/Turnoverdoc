package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.model.Order;

import java.io.Writer;
import java.util.List;

public interface ExportService {
    void writeOrderToCsv(Writer writer);
}
