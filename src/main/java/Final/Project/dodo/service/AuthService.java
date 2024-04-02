package Final.Project.dodo.service;


import Final.Project.dodo.model.request.auth.AuthRequest;
import Final.Project.dodo.model.request.auth.ValidateEmailReq;

import java.util.Date;

public interface AuthService {
    String auth(AuthRequest request);

    String validate(ValidateEmailReq req);
    Long validateToken(String token);

    String generateTempPass();

    Boolean checkTime(Date sendTime);

    String getClaim(String token);
}
