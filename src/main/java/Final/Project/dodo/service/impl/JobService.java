package Final.Project.dodo.service.impl;

import Final.Project.dodo.model.dto.OrderDto;
import Final.Project.dodo.model.enums.OrderStatus;
import Final.Project.dodo.service.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;

@Component

public class JobService {


    private final OrderService orderService;

    public JobService(OrderService orderService) {
        this.orderService = orderService;
    }


    @Scheduled(fixedRate = 60000)
    private void testSchedule(){
       orderService.checkOrders();
    }
}
