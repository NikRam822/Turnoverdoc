package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.services.ExportService;
import com.turnoverdoc.turnover.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
@Slf4j
public class ExportServiceImpl implements ExportService {

    private final Logger LOGGER = log;
    private final OrderService orderService;

    @Autowired
    public ExportServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void writeOrderToCsv(Writer writer) {
        List<Order> orders = orderService.getAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("ID", "STATUS", "USER_ID", "USERNAME", "PASSPORT", "P45", "P60", "P80", "CONTRACT", "DATE");
            for (Order order : orders) {
                if (order.getUser() != null) {
                    csvPrinter.printRecord(order.getId(),
                            order.getStatus(),
                            order.getUser().getId(),
                            order.getUser().getUsername(),
                            order.getPassportPath(),
                            order.getP45Path(),
                            order.getP60Path(),
                            order.getP80Path(),
                            order.getContractPath(),
                            (order.getTimestampDate()!=null) ? order.getTimestampDate():""
                            );
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error While writing CSV ", e);
        }
    }
}
