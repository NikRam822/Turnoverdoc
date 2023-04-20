package com.turnoverdoc.turnover.controllers.admin;

import com.turnoverdoc.turnover.dto.ChangeStatusDto;
import com.turnoverdoc.turnover.dto.FilterOrderDto;
import com.turnoverdoc.turnover.dto.order.FullOrderDto;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.services.ExportService;
import com.turnoverdoc.turnover.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.turnoverdoc.turnover.error.ErrorsContainer.TURN2;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping(value = "/api/v1/admin")
public class AdminController {

    private ExportService exportService;
    private OrderService orderService;

    @Autowired
    public void setExportService(ExportService exportService) {
        this.exportService = exportService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/export-orders")
    public void getAllOrdersInCsv(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=orders.csv");
        exportService.writeOrderToCsv(servletResponse.getWriter());
    }

    @PostMapping("/changeStatus")
    public ResponseEntity<String> changeStatus(@ModelAttribute ChangeStatusDto changeStatusDto) throws ErrorDto {
        Order order = orderService.findById(Long.parseLong(changeStatusDto.getOrderId()));

        if (order != null) {
            orderService.changeStatus(order, changeStatusDto.getStatus());
            return new ResponseEntity<>("Order successful saved", HttpStatus.OK);
        }
        throw TURN2;
    }

    @PostMapping("order/get-filtered-orders")
    public ResponseEntity<List<FullOrderDto>> getFilteredOrders(@ModelAttribute FilterOrderDto filterDto){
        List<Order> orders = orderService.getFilteredOrders(filterDto);
        return new ResponseEntity<>(FullOrderDto.toFullOrderDtoList(orders), HttpStatus.OK);
    }
}
