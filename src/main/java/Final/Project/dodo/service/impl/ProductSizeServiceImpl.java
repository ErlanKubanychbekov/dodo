package Final.Project.dodo.service.impl;

import Final.Project.dodo.base.BaseServiceImpl;
import Final.Project.dodo.dao.ProductSizeRep;
import Final.Project.dodo.model.dto.ProductDto;
import Final.Project.dodo.model.dto.ProductSizeDto;
import Final.Project.dodo.model.entities.ProductSize;
import Final.Project.dodo.model.mapper.ProductSizeMapper;
import Final.Project.dodo.model.request.create.ProductSizeCreateRequest;
import Final.Project.dodo.model.request.update.ProductSizeUpdateRequest;
import Final.Project.dodo.model.response.FilterResponse;
import Final.Project.dodo.service.ProductService;
import Final.Project.dodo.service.ProductSizeService;
import Final.Project.dodo.service.SizeService;
import Final.Project.dodo.utils.ResourceBundelLanguage;
import Final.Project.dodo.utils.language;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSizeServiceImpl extends BaseServiceImpl<ProductSize, ProductSizeRep, ProductSizeDto, ProductSizeMapper> implements ProductSizeService {
    public ProductSizeServiceImpl(ProductSizeRep rep, ProductSizeMapper mapper, ProductService productService, SizeService sizeService) {
        super(rep, mapper);
        this.productService = productService;
        this.sizeService = sizeService;
    }

    private final ProductService productService;
    private final SizeService sizeService;

    @Override
    public String create(ProductSizeCreateRequest request,Integer lang) {
        ProductSizeDto dto = new ProductSizeDto();
        dto.setProduct(productService.findById(request.getProductId()));
        dto.setSize(sizeService.findById(request.getSizeId()));
        dto.setPrice(request.getPrice());
        dto.setActive(request.getActive());

        save(dto);
        return ResourceBundelLanguage.periodMessage(language.getLanguage(lang),"createSuccessful");
    }


    @Override
    public ProductSizeDto update(ProductSizeUpdateRequest request) {
        ProductSizeDto dto = findById(request.getId());
        dto.setAddDate(dto.getAddDate());
        dto.setProduct(productService.findById(request.getProductId()));
        dto.setSize(sizeService.findById(request.getSizeId()));
        dto.setPrice(request.getPrice());
        dto.setActive(request.getActive());
        return update(dto);
    }

    @Override
    public Boolean delete(Long id) {
        return delete(findById(id));
    }

    @Override
    public ProductSizeDto findByProductId(Long id) {
        return rep.findByProductId(id);
    }
    @Override
    public List<FilterResponse> filter(Long sizeId, BigDecimal fromPrice, BigDecimal toPrice, String name, Long categoryId) {
        List<ProductSize> productSizes = rep.filterProduct(sizeId,  fromPrice,  toPrice,  name,  categoryId);
        List<FilterResponse> filterResponseList = new ArrayList<>();

        for (ProductSize productSize: productSizes) {
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setProductSizeId(productSize.getId());
            filterResponse.setPrice(productSize.getPrice());
            filterResponse.setProductName(productSize.getProduct().getName());
            filterResponse.setSizeName(productSize.getSize().getName());
            filterResponse.setCategoriesName(productSize.getProduct().getCategories().getName());

            filterResponseList.add(filterResponse);

        }
        return filterResponseList;



    }
}
