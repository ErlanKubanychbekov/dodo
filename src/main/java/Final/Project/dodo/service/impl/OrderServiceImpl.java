package Final.Project.dodo.service.impl;

import Final.Project.dodo.base.BaseServiceImpl;
import Final.Project.dodo.dao.OrderRep;
import Final.Project.dodo.dao.projection.OrderProductProjection;
import Final.Project.dodo.model.dto.*;
import Final.Project.dodo.model.entities.Order;
import Final.Project.dodo.model.enums.OrderStatus;
import Final.Project.dodo.model.mapper.OrderMapper;
import Final.Project.dodo.model.request.ProductOrderList;
import Final.Project.dodo.model.request.create.OrderCreateRequest;
import Final.Project.dodo.model.response.OrderHistoryResponse;
import Final.Project.dodo.model.response.ProductResponse;
import Final.Project.dodo.service.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, OrderRep, OrderDto, OrderMapper> implements OrderService {
    public OrderServiceImpl(OrderRep rep, OrderMapper mapper, UserService userService,
                            AddressService addressService, OrderProductService orderProductService, ProductSizeService productSizeService, ProductService productService) {
        super(rep, mapper);
        this.userService = userService;
        this.addressService = addressService;
        this.orderProductService = orderProductService;
        this.productSizeService = productSizeService;
        this.productService = productService;
    }
    private final UserService userService;
    private final AddressService addressService;
    private final OrderProductService orderProductService;
    private  final ProductSizeService productSizeService;
    private final ProductService productService;

    @Override
    public List<OrderDto> getByUserId(Long userId) {
        return mapper.toDtos(rep.findByUserId(userId),context);
    }

    @Override
    public OrderDto create(OrderCreateRequest request) {
        int dodocoins;
        BigDecimal totalPrice= BigDecimal.ZERO;
        AddressDto addressDto = addressService.findById(request.getAddressId());
        UserDto userDto = userService.findById(request.getUserId());

        OrderDto orderDto =new  OrderDto();
        orderDto.setOrderDate(request.getOrderDate());
        orderDto.setUser(userDto);
        orderDto.setAddress(addressDto);
        orderDto.setPaymentType(request.getPaymentType());
        save(orderDto);

        for (ProductOrderList item: request.getProductOrderLists()) {
            OrderProductDto orderProductDto = new OrderProductDto();
            orderProductDto.setProduct(productService.findById(item.getProductId()));
            orderProductDto.setOrder(orderDto);
            orderProductDto.setPrice(productSizeService.findById(item.getProductId()).getPrice());
            totalPrice =  totalPrice.add(productSizeService.findById(item.getProductId()).getPrice());


           orderProductService.save(orderProductDto);

        }


        dodocoins = totalPrice.multiply(BigDecimal.valueOf(0.20)).intValue();
        orderDto.setDodoCoins(dodocoins);
        orderDto.setTotalPrice(totalPrice);
        orderDto.setOrderStatus(OrderStatus.CREATED);
        orderDto=updated(orderDto);


        if(userDto.getDodoCoins() == null){
            userDto.setDodoCoins(dodocoins);
        }else {
            userDto.setDodoCoins(dodocoins + userDto.getDodoCoins());
        }

        userService.updated(userDto);



        return orderDto;

    }

    @Override
    public OrderDto updated(OrderDto request) {
        OrderDto dto = new OrderDto();
        dto.setId(request.getId());
        dto.setUser(request.getUser());
        dto.setAddress(request.getAddress());
        dto.setTotalPrice(request.getTotalPrice());
        dto.setOrderStatus(request.getOrderStatus());
        dto.setOrderDate(request.getOrderDate());
        dto.setPaymentType(request.getPaymentType());
        dto.setOrderStatus(request.getOrderStatus());
        dto.setDodoCoins(request.getDodoCoins());

        return update(dto);
    }

    @Override
    public Boolean delete(Long id) {
        return delete(findById(id));
    }

    @Override
    public List<OrderHistoryResponse> getOrderHistory(Long userId) {
        List<OrderDto> orderDtos = getByUserId(userId);
        List<OrderHistoryResponse> historyResponse = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(OrderDto dto: orderDtos) {
            List<OrderProductProjection> productOrder = orderProductService.findAllByOrderId(dto.getId());
            OrderHistoryResponse response = new OrderHistoryResponse();
            List<ProductResponse> productResponseList = new ArrayList<>();

            response.setId(dto.getId());
            response.setOrderDate(dto.getOrderDate());
            response.setAddressStreet(dto.getAddress().getStreet());
            response.setAddressStreetNum(dto.getAddress().getNum());
            for (OrderProductProjection  productDto : productOrder ) {
                ProductResponse productResponse = new ProductResponse();
                productResponse.setId(productDto.getId());
                productResponse.setName(productDto.getName());
                productResponse.setDescription(productDto.getDescription());
                productResponse.setCategoriesName(productService.findById(productDto.getProductId()).getCategories().getName());
                productResponseList.add(productResponse);
            }
            response.setProductList(productResponseList);
            response.setTotalPrice(totalPrice.add(dto.getTotalPrice()));
            historyResponse.add(response);

        }
        return historyResponse;
    }
}
