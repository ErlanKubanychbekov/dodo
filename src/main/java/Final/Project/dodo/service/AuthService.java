package Final.Project.dodo.service;


import Final.Project.dodo.model.request.auth.AuthRequest;
import Final.Project.dodo.model.request.auth.ValidateEmailReq;

import java.util.Date;

public interface AuthService {
    String auth(AuthRequest request, Integer langugeOrdinal);

    String validate(ValidateEmailReq req, Integer languageOrdinal);
    Long validateToken(String token, Integer lang);





    String getClaim(String token, Integer lang);


}
