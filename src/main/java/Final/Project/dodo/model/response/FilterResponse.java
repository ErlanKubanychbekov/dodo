package Final.Project.dodo.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FilterResponse {
    Long productSizeId;
    BigDecimal price;
    String productName;
    String categoriesName;
    String sizeName;



}
