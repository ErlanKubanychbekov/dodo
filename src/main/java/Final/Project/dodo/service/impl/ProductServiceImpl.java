package Final.Project.dodo.service.impl;

import Final.Project.dodo.base.BaseServiceImpl;
import Final.Project.dodo.dao.ProductRep;
import Final.Project.dodo.model.dto.ProductDto;
import Final.Project.dodo.model.entities.Product;
import Final.Project.dodo.model.mapper.ProductMapper;
import Final.Project.dodo.model.request.create.ProductCreateRequest;
import Final.Project.dodo.model.request.update.ProductUpdateRequest;
import Final.Project.dodo.service.CategoriesService;
import Final.Project.dodo.service.ProductService;
import Final.Project.dodo.utils.ResourceBundelLanguage;
import Final.Project.dodo.utils.language;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, ProductRep, ProductDto, ProductMapper> implements ProductService {
    public ProductServiceImpl(ProductRep rep, ProductMapper mapper, CategoriesService categoriesService) {
        super(rep, mapper);
        this.categoriesService = categoriesService;
    }
    private final CategoriesService categoriesService;

    @Override
    public String create(ProductCreateRequest request, Integer lang) {
        ProductDto dto = new ProductDto();
        dto.setName(request.getName());
        dto.setDescription(request.getDescription());
        dto.setLogo(request.getLogo());
        dto.setCategories(categoriesService.findById(request.getCategoryId()));
        save(dto);
        return ResourceBundelLanguage.periodMessage(language.getLanguage(lang),"createSuccessful");
    }


    @Override
    public ProductDto update(ProductUpdateRequest request) {
        ProductDto dto = new ProductDto();
        dto.setId(request.getId());
        dto.setName(request.getName());
        dto.setDescription(request.getDescription());
        dto.setLogo(request.getLogo());
        dto.setCategories(categoriesService.findById(request.getCategoryId()));
        return update(dto);
    }

    @Override
    public Boolean delete(Long id) {
        return delete(findById(id));
    }



}
