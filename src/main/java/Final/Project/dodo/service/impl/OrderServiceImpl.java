package Final.Project.dodo.service.impl;

import Final.Project.dodo.base.BaseServiceImpl;
import Final.Project.dodo.dao.OrderRep;
import Final.Project.dodo.dao.projection.OrderProductProjection;
import Final.Project.dodo.exception.NotFoundException;
import Final.Project.dodo.model.dto.*;
import Final.Project.dodo.model.entities.Order;
import Final.Project.dodo.model.entities.OrderProduct;
import Final.Project.dodo.model.enums.OrderStatus;
import Final.Project.dodo.model.mapper.OrderMapper;
import Final.Project.dodo.model.request.ProductOrderList;
import Final.Project.dodo.model.request.RepeatOrderRequest;
import Final.Project.dodo.model.request.create.OrderCreateRequest;
import Final.Project.dodo.model.response.OrderHistoryResponse;
import Final.Project.dodo.model.response.ProductResponse;
import Final.Project.dodo.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, OrderRep, OrderDto, OrderMapper> implements OrderService {
    public OrderServiceImpl(OrderRep rep, OrderMapper mapper, UserService userService,
                            AddressService addressService,
                            OrderProductService orderProductService,
                            ProductSizeService productSizeService, ProductService productService) {
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
        if (request.getProductOrderLists().isEmpty()){
            throw new RuntimeException("Select product");
        }

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
    public List<OrderHistoryResponse> getOrderHistory(Long userId, int limit, int offset) {
        List<OrderDto> orderDtos = getByUserId(userId);
        List<OrderHistoryResponse> historyResponse = new ArrayList<>();

        for (int i = offset; i < Math.min(orderDtos.size(), offset + limit); i++) {
            OrderDto dto = orderDtos.get(i);
            List<OrderProduct> productOrder = orderProductService.findAllByOrderId(dto.getId());
            OrderHistoryResponse response = new OrderHistoryResponse();
            List<ProductResponse> productResponseList = new ArrayList<>();
            BigDecimal totalPrice = BigDecimal.ZERO;

            response.setId(dto.getId());
            response.setOrderDate(dto.getOrderDate());
            response.setAddressStreet(dto.getAddress().getStreet());
            response.setAddressStreetNum(dto.getAddress().getNum());
            for (OrderProduct productDto : productOrder) {
                ProductResponse productResponse = new ProductResponse();
                productResponse.setId(productDto.getProduct().getId());
                productResponse.setName(productDto.getProduct().getName());
                productResponse.setDescription(productDto.getProduct().getDescription());
                productResponse.setCategoriesName(productDto.getProduct().getCategories().getName());

                productResponseList.add(productResponse);

                totalPrice = totalPrice.add(productDto.getPrice());
            }
            response.setProductList(productResponseList);
            response.setTotalPrice(totalPrice);

            historyResponse.add(response);
        }

        return historyResponse;
    }

    @Override
    public OrderCreateRequest repeatOrder(Long orderId, Long userId) {
        OrderDto orderDto = findById(orderId);
        List<ProductOrderList> productOrderLists = new ArrayList<>();

        OrderCreateRequest createRequest = new OrderCreateRequest();
        createRequest.setUserId(userId);
        createRequest.setOrderDate(orderDto.getOrderDate());
        createRequest.setPaymentType(orderDto.getPaymentType());
        createRequest.setAddressId(orderDto.getAddress().getId());

        for (OrderProduct dto : orderProductService.findAllByOrderId(orderDto.getId())){
            ProductOrderList productOrderList = new ProductOrderList();
            productOrderList.setProductId(dto.getProduct().getId());
            productOrderList.setPrice(dto.getPrice());
            productOrderLists.add(productOrderList);
        }
        createRequest.setProductOrderLists(productOrderLists);



        return createRequest;


    }

    @Override
    public List<OrderDto> findNewOrders() {
        return rep.findNewOrders();
    }
}
