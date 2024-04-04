package Final.Project.dodo.controller;

import Final.Project.dodo.model.dto.UserDto;
import Final.Project.dodo.model.request.create.UserCreateRequest;
import Final.Project.dodo.model.request.update.UserUpdateRequest;
import Final.Project.dodo.service.AccountService;
import Final.Project.dodo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;
    private final AccountService accountService;

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody UserCreateRequest request, @RequestParam Integer langugeOrdenal) {
        return ResponseEntity.ok(service.create(request,langugeOrdenal));
    }

    @PutMapping("Update")
    public ResponseEntity<?> update(@RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(service.update(request));
    }
    @GetMapping("getById")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
    @GetMapping("/getUserByToken")
    public ResponseEntity<?> getUserByToken(@RequestHeader String token, @RequestParam Integer lang ){
        return ResponseEntity.ok(service.getUserByToken(token, lang));
    }
    @GetMapping("UserInfo")
    public ResponseEntity<?> getUserInfo(@RequestHeader String token, int offset, int limit,
                                         @RequestParam(required = false,defaultValue = "3")
                                         Integer languageOrdinal){
        return ResponseEntity.ok(accountService.getUserInfo(token, offset, limit ,languageOrdinal));

    }
}

