package Final.Project.dodo.model.request;

import Final.Project.dodo.model.enums.PaymentType;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RepeatOrderRequest {
    Long orderId;
    LocalDateTime orderDate;
    PaymentType paymentType;
    Long addressId;
}
