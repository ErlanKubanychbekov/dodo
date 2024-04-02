package Final.Project.dodo.dao.projection;

import Final.Project.dodo.model.dto.OrderDto;
import Final.Project.dodo.model.dto.ProductDto;

import java.math.BigDecimal;

public interface OrderProductProjection {


    Long getProductId();
    Long getId();
    String getName();
    String getDescription();
    Long getCategories();
    Long getOrderId();
    BigDecimal getPrice();
}
