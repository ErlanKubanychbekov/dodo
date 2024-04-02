package Final.Project.dodo.service;

import Final.Project.dodo.base.BaseService;
import Final.Project.dodo.model.dto.CategoriesDto;
import Final.Project.dodo.model.dto.OrderDto;
import Final.Project.dodo.model.request.create.CategoriesCreateRequest;
import Final.Project.dodo.model.request.create.OrderCreateRequest;
import Final.Project.dodo.model.request.update.CategoriesUpdateRequest;
import Final.Project.dodo.model.request.update.OrderUpdateRequest;
import Final.Project.dodo.model.response.OrderHistoryResponse;

import java.util.List;


public interface OrderService extends BaseService<OrderDto> {
    OrderDto create(OrderCreateRequest request);

    OrderDto updated(OrderDto request);

    Boolean delete(Long id);


    List<OrderDto> getByUserId(Long userId);

    List<OrderHistoryResponse> getOrderHistory(Long userId);
}
