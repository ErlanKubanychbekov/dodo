package Final.Project.dodo.controller;


import Final.Project.dodo.authservice.utils.JwtProvider;
import Final.Project.dodo.model.dto.OrderDto;
import Final.Project.dodo.model.request.create.OrderCreateRequest;
import Final.Project.dodo.model.request.update.OrderUpdateRequest;
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
    public ResponseEntity<?> save( @RequestHeader String  token, @RequestBody OrderCreateRequest request) {
        Long userId = jwtProvider.validateToken(token);
        request.setUserId(userId);
        return ResponseEntity.ok(service.create(request));
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
    public ResponseEntity<?> getOrderHistory(@RequestHeader String token){
        Long userId = jwtProvider.validateToken(token);
        return ResponseEntity.ok(service.getOrderHistory(userId));

    }

}


