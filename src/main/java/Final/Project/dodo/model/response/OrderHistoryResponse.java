package Final.Project.dodo.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data

public class OrderHistoryResponse {
    Long id;
    LocalDateTime orderDate;
    String addressStreet;
    String addressStreetNum;
    List<ProductResponse> productList;
    BigDecimal totalPrice;

}
