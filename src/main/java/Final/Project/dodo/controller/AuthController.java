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
    ResponseEntity<?> auth(@RequestBody AuthRequest request ,
                           @RequestParam(required = false,defaultValue = "3") Integer languageOrdinal ){
        return ResponseEntity.ok(authService.auth(request, languageOrdinal));
    }

    @PostMapping ("/check")
    ResponseEntity<?> validate(@RequestBody ValidateEmailReq emailReq,@RequestParam(required = false,defaultValue = "3")  Integer languageOrdinal){
        return ResponseEntity.ok(authService.validate(emailReq,languageOrdinal));
    }

    @GetMapping ("/validateToken")
    ResponseEntity<?> validateToken(@RequestHeader String token,
                                    @RequestParam(required = false,defaultValue = "3") Integer lang){
        return ResponseEntity.ok(authService.validateToken(token,lang));
    }

    @GetMapping ("/Claim")
    ResponseEntity<?> getClaim(@RequestHeader String token,
                               @RequestParam(required = false,defaultValue = "3") Integer lang){
        return ResponseEntity.ok(authService.getClaim(token, lang));
    }



}
