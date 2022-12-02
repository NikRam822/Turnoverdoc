package com.turnoverdoc.turnover.controllers.admin;

import com.turnoverdoc.turnover.services.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/admin")
public class Export {

    private ExportService exportService;
    @Autowired
    public void setExportService(ExportService exportService) {
        this.exportService = exportService;
    }


    @GetMapping(path = "/export-orders")
    public void getAllOrdersInCsv(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=orders.csv");
        exportService.writeOrderToCsv(servletResponse.getWriter());
    }
}
