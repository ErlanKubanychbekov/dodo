package Final.Project.dodo.service.impl;


import Final.Project.dodo.base.BaseServiceImpl;
import Final.Project.dodo.dao.OrderProductRep;
import Final.Project.dodo.dao.projection.OrderProductProjection;
import Final.Project.dodo.model.dto.OrderDto;
import Final.Project.dodo.model.dto.OrderProductDto;
import Final.Project.dodo.model.entities.OrderProduct;
import Final.Project.dodo.model.mapper.OrderProductMapper;
import Final.Project.dodo.model.request.create.OrderProductCreateRequest;
import Final.Project.dodo.model.request.update.OrderProductUpdateRequest;
import Final.Project.dodo.service.OrderProductService;
import Final.Project.dodo.service.OrderService;
import Final.Project.dodo.service.ProductService;
import Final.Project.dodo.service.ProductSizeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProductServiceImpl extends BaseServiceImpl<OrderProduct, OrderProductRep, OrderProductDto, OrderProductMapper> implements OrderProductService {
    public OrderProductServiceImpl(OrderProductRep rep, OrderProductMapper mapper,
                                   ProductService productService) {
        super(rep, mapper);
        this.productService = productService;
    }

    private final ProductService productService;


//    @Override
//    public OrderProductDto create( Long productSizeId) {
//        OrderProductDto dto = new OrderProductDto();
////        dto.setOrder(orderDto);
//        dto.setProduct(productSizeService.findById(productSizeId));
////        dto.setPrice(productSizeService.findByProductId(request.getProductSizeId()).getPrice());
//        return save(dto);
//    }

    @Override
    public OrderProductDto update(OrderProductUpdateRequest request) {
        OrderProductDto dto = new OrderProductDto();
        dto.setId(request.getId());
        dto.setOrder(request.getOrderDto());
        dto.setProduct(productService.findById(request.getProductId()));
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
