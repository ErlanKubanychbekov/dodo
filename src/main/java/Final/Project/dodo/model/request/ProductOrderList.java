package Final.Project.dodo.model.request;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductOrderList {
    Long productSizeId;
    BigDecimal price;
}
