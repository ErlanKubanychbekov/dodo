package Final.Project.dodo.model.request.update;

import Final.Project.dodo.model.dto.OrderDto;
import Final.Project.dodo.model.entities.Order;
import Final.Project.dodo.model.entities.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderProductUpdateRequest {
    Long id;
    Long productId;
    OrderDto orderDto;
    BigDecimal price;
}
