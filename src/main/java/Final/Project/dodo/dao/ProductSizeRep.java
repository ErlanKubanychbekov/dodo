package Final.Project.dodo.dao;

import Final.Project.dodo.base.BaseRep;
import Final.Project.dodo.model.dto.ProductDto;
import Final.Project.dodo.model.dto.ProductSizeDto;
import Final.Project.dodo.model.entities.ProductSize;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductSizeRep extends BaseRep<ProductSize> {
    ProductSizeDto findByProductId(Long id);

    @Query("SELECT ps FROM ProductSize ps " +
            "INNER JOIN ps.product p " +
            "WHERE (:sizeId IS NULL OR ps.size.id = :sizeId) " +
            "AND (:fromPrice IS NULL OR ps.price >= :fromPrice) " +
            "AND (:toPrice IS NULL OR ps.price <= :toPrice) " +
            "AND (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:categoryId IS NULL OR p.categories.id = :categoryId)")
    List<ProductSize> filterProduct(Long sizeId, BigDecimal fromPrice,
                                    BigDecimal toPrice, String name, Long categoryId);

}
