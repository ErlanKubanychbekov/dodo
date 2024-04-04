package Final.Project.dodo.service.impl;


import Final.Project.dodo.base.BaseServiceImpl;
import Final.Project.dodo.dao.OrderProductRep;
import Final.Project.dodo.model.dto.OrderProductDto;
import Final.Project.dodo.model.entities.OrderProduct;
import Final.Project.dodo.model.mapper.OrderProductMapper;
import Final.Project.dodo.model.request.update.OrderProductUpdateRequest;
import Final.Project.dodo.service.OrderProductService;
import Final.Project.dodo.service.ProductSizeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductServiceImpl extends BaseServiceImpl<OrderProduct, OrderProductRep, OrderProductDto, OrderProductMapper> implements OrderProductService {
    public OrderProductServiceImpl(OrderProductRep rep, OrderProductMapper mapper, ProductSizeService productSizeService) {
        super(rep, mapper);
        this.productSizeService = productSizeService;
    }

    private final ProductSizeService productSizeService;




    @Override
    public OrderProductDto update(OrderProductUpdateRequest request) {
        OrderProductDto dto = new OrderProductDto();
        dto.setId(request.getId());
        dto.setOrder(request.getOrderDto());
        dto.setProductSize(productSizeService.findById(request.getProductSizeId()));
        dto.setPrice(request.getPrice());
        return update(dto);
    }

    @Override
    public Boolean delete(Long id) {
        return delete(findById(id));
    }

    @Override
    public List<OrderProduct> findAllByOrderId(Long id) {
        return rep.findAllByOrderId(id);
    }
}
