package Final.Project.dodo.model.dto;

import Final.Project.dodo.base.BaseDto;
import Final.Project.dodo.model.entities.ProductSize;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class OrderProductDto extends BaseDto {
    ProductSizeDto productSize;
    OrderDto order;
    BigDecimal price;
}
