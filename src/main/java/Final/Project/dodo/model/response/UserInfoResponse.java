package Final.Project.dodo.model.response;

import Final.Project.dodo.dao.projection.AddressResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserInfoResponse {
    String name;
    String email;
    Integer dodCoins;
    Integer OrderCount;
    List<OrderHistoryResponse> historyResponses;
    List<AddressResponse> addressList;
}