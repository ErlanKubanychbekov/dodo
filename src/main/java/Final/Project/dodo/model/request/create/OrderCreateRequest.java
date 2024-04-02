package Final.Project.dodo.model.request.create;



import Final.Project.dodo.model.enums.PaymentType;
import Final.Project.dodo.model.request.ProductOrderList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreateRequest {
    LocalDateTime orderDate;
    PaymentType paymentType;
    Long userId;
    Long addressId;

    List<ProductOrderList> productOrderLists;
}
