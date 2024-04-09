package Final.Project.dodo.controller;

import Final.Project.dodo.model.dto.ProductSizeDto;
import Final.Project.dodo.model.request.create.ProductSizeCreateRequest;
import Final.Project.dodo.model.request.update.ProductSizeUpdateRequest;
import Final.Project.dodo.service.ProductSizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-sizes")
public class ProductSizeController {

    private final ProductSizeService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductSizeCreateRequest request,
                                    @RequestParam(required = false,defaultValue = "3") Integer lang) {
        return ResponseEntity.ok(service.create(request,lang));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ProductSizeUpdateRequest request) {
        return ResponseEntity.ok(service.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("getById")
    public ResponseEntity<?> getById(Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("filter")
    public ResponseEntity<?> getProductByFilter(@RequestParam(required = false) Long sizeId,
                                                @RequestParam(required = false) BigDecimal fromPrice,
                                                @RequestParam(required = false) BigDecimal toPrice,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) Long categoryId){
        return ResponseEntity.ok(service.filter(sizeId,fromPrice, toPrice,name, categoryId));
    }
}
