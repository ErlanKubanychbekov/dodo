package Final.Project.dodo.service.impl;

import Final.Project.dodo.utils.ResourceBundelLanguage;
import Final.Project.dodo.utils.language;
import Final.Project.dodo.base.BaseServiceImpl;
import Final.Project.dodo.dao.OrderRep;
import Final.Project.dodo.exception.EmptyListException;
import Final.Project.dodo.model.dto.*;
import Final.Project.dodo.model.entities.Order;
import Final.Project.dodo.model.entities.OrderProduct;
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
                            AddressService addressService,
                            OrderProductService orderProductService,
                            ProductSizeService productSizeService) {
        super(rep, mapper);
        this.userService = userService;
        this.addressService = addressService;
        this.orderProductService = orderProductService;
        this.productSizeService = productSizeService;

    }

    private final UserService userService;
    private final AddressService addressService;
    private final OrderProductService orderProductService;
    private final ProductSizeService productSizeService;
    @Override
    public String create(OrderCreateRequest request, Integer langugeOrdinal) {
        if (request.getProductOrderLists().isEmpty()) {
            throw new EmptyListException(ResourceBundelLanguage.
                    periodMessage(language.getLanguage(langugeOrdinal),"selectproduct"));
        }

        int dodocoins;
        BigDecimal totalPrice = BigDecimal.ZERO;
        AddressDto addressDto = addressService.findById(request.getAddressId());
        UserDto userDto = userService.findById(request.getUserId());

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderDate(request.getOrderDate());
        orderDto.setUser(userDto);
        orderDto.setAddress(addressDto);
        orderDto.setPaymentType(request.getPaymentType());


        for (ProductOrderList item : request.getProductOrderLists()) {
            totalPrice = totalPrice.add(item.getPrice());

        }

        dodocoins = totalPrice.multiply(BigDecimal.valueOf(0.20)).intValue();
        orderDto.setDodoCoins(dodocoins);
        orderDto.setTotalPrice(totalPrice);
        orderDto.setOrderStatus(OrderStatus.CREATED);
        save(orderDto);

        for (ProductOrderList item : request.getProductOrderLists()) {
            totalPrice = totalPrice.add(item.getPrice());
            OrderProductDto orderProductDto = new OrderProductDto();

            ProductSizeDto productSizeDto = productSizeService.findById(item.getProductSizeId());
            orderProductDto.setProductSize(productSizeDto);
            orderProductDto.setOrder(orderDto);
            orderProductDto.setPrice(productSizeDto.getPrice());

            orderProductService.save(orderProductDto);
        }
        if (userDto.getDodoCoins() == null) {
            userDto.setDodoCoins(dodocoins);
        } else {
            userDto.setDodoCoins(dodocoins + userDto.getDodoCoins());
        }

        userService.updated(userDto);

        return ResourceBundelLanguage.periodMessage(language.getLanguage(langugeOrdinal),
                "createSuccessful");
    }


    @Override
    public List<OrderDto> getByUserId(Long userId) {
        return mapper.toDtos(rep.findByUserId(userId), context);
    }

    @Override
    public void checkOrders() {
        List<OrderDto> orderList = findNewOrders();
        for (OrderDto dto : orderList) {
            if (dto.getUpdateDate().plusMinutes(30).isEqual(dto.getOrderDate())
                    || dto.getUpdateDate().plusMinutes(30).isBefore(dto.getOrderDate())) {
                dto.setOrderStatus(OrderStatus.PREPARING);
                update(dto);
            }
        }
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
                productResponse.setId(productDto.getProductSize().getId());
                productResponse.setName(productDto.getProductSize().getProduct().getName());
                productResponse.setDescription(productDto.getProductSize().getProduct().getDescription());
                productResponse.setCategoriesName(productDto.getProductSize().getProduct().getCategories().getName());

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

        for (OrderProduct dto : orderProductService.findAllByOrderId(orderDto.getId())) {
            ProductOrderList productOrderList = new ProductOrderList();
            productOrderList.setProductSizeId(dto.getProductSize().getId());
            productOrderList.setPrice(dto.getProductSize().getPrice());

            productOrderLists.add(productOrderList);
        }
        createRequest.setProductOrderLists(productOrderLists);


        return createRequest;


    }

    @Override
    public List<OrderDto> findNewOrders() {
        return mapper.toDtos(rep.findNewOrders(), context);
    }

}
