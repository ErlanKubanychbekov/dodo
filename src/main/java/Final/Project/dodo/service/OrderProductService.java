package Final.Project.dodo.service;

import Final.Project.dodo.base.BaseService;
import Final.Project.dodo.dao.projection.OrderProductProjection;
import Final.Project.dodo.model.dto.OrderDto;
import Final.Project.dodo.model.dto.OrderProductDto;
import Final.Project.dodo.model.request.create.OrderProductCreateRequest;
import Final.Project.dodo.model.request.update.OrderProductUpdateRequest;

import java.util.List;

public interface OrderProductService extends BaseService<OrderProductDto> {
//    OrderProductDto create (Long productSizeId);
    OrderProductDto update(OrderProductUpdateRequest request);
    Boolean delete(Long id);
    List<OrderProductProjection> findAllByOrderId(Long id);
}
