package Final.Project.dodo.controller;


import Final.Project.dodo.utils.JwtProvider;
import Final.Project.dodo.model.dto.OrderDto;
import Final.Project.dodo.model.request.create.OrderCreateRequest;

import Final.Project.dodo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/orders")
public class OrderController {

    private final OrderService service;
    private final JwtProvider jwtProvider;

    @PostMapping("save")
    public ResponseEntity<?> save( @RequestHeader String  token,
                                   @RequestBody OrderCreateRequest request,
                                   @RequestParam(required = false,defaultValue = "3") Integer languageOrdinal) {
        Long userId = jwtProvider.validateToken(token, languageOrdinal);
        request.setUserId(userId);
        return ResponseEntity.ok(service.create(request,languageOrdinal));
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody OrderDto request) {
        return ResponseEntity.ok(service.update(request));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("getAll")
    public ResponseEntity<List<OrderDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("getById")
    public ResponseEntity<OrderDto> getById(@RequestParam Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("orderHistory")
    public ResponseEntity<?> getOrderHistory(
            @RequestHeader String token,int limit, int offset,@RequestParam(required = false,defaultValue = "3") Integer lang){
        Long userId = jwtProvider.validateToken(token,lang);
        return ResponseEntity.ok(service.getOrderHistory(userId,limit,  offset));

    }

    @PostMapping("repeatOrder")
    public  ResponseEntity<?> repeatOrder(@RequestHeader String token
            ,@RequestParam Long orderId, @RequestParam(required = false, defaultValue = "3")
                                              Integer languageOrdinal){
        Long userId = jwtProvider.validateToken(token,languageOrdinal);
        return ResponseEntity.ok(service.repeatOrder(orderId,userId));
    }

}


