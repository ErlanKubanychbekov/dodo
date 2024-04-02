package Final.Project.dodo.dao;

import Final.Project.dodo.base.BaseRep;
import Final.Project.dodo.model.dto.ProductSizeDto;
import Final.Project.dodo.model.entities.ProductSize;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSizeRep extends BaseRep<ProductSize> {
    ProductSizeDto findByProductId(Long id);
}
