package Final.Project.dodo.controller;

import Final.Project.dodo.model.request.auth.AuthRequest;
import Final.Project.dodo.model.request.auth.ValidateEmailReq;
import Final.Project.dodo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping
    ResponseEntity<?> auth(@RequestBody AuthRequest request ){
        return ResponseEntity.ok(authService.auth(request));
    }

    @PostMapping ("/check")
    ResponseEntity<?> validate(@RequestBody ValidateEmailReq emailReq){
        return ResponseEntity.ok(authService.validate(emailReq));
    }

    @GetMapping ("/validateToken")
    ResponseEntity<?> validateToken(String token){
        return ResponseEntity.ok(authService.validateToken(token));
    }

    @GetMapping ("/Claim")
    ResponseEntity<?> getClaim(String token){
        return ResponseEntity.ok(authService.getClaim(token));
    }



}
