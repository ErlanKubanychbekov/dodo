package Final.Project.dodo.model.request.auth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidateEmailReq {
    String email;
    String password;
}
