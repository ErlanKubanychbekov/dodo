package Final.Project.dodo.model.request.update;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    Long id;
    String name;
    String email;
    String phone;
    Integer dodoCoins;
    String tempPass;
    Date sendDate;
}
