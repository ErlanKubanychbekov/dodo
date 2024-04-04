package Final.Project.dodo.service;

import Final.Project.dodo.base.BaseService;
import Final.Project.dodo.model.dto.CategoriesDto;
import Final.Project.dodo.model.dto.ProductDto;
import Final.Project.dodo.model.dto.ProductSizeDto;
import Final.Project.dodo.model.entities.ProductSize;
import Final.Project.dodo.model.request.create.CategoriesCreateRequest;
import Final.Project.dodo.model.request.create.ProductSizeCreateRequest;
import Final.Project.dodo.model.request.update.CategoriesUpdateRequest;
import Final.Project.dodo.model.request.update.ProductSizeUpdateRequest;
import Final.Project.dodo.model.response.FilterResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductSizeService extends BaseService<ProductSizeDto> {
    String create(ProductSizeCreateRequest request, Integer lang);

    ProductSizeDto update(ProductSizeUpdateRequest request);

    Boolean delete(Long id);


    ProductSizeDto findByProductId(Long id);
    List<FilterResponse> filter(Long sizeId, BigDecimal fromPrice, BigDecimal toPrice, String name, Long categoryId);
}
