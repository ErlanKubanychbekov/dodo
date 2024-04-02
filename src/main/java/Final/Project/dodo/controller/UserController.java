package Final.Project.dodo.controller;

import Final.Project.dodo.model.dto.UserDto;
import Final.Project.dodo.model.request.create.UserCreateRequest;
import Final.Project.dodo.model.request.update.UserUpdateRequest;
import Final.Project.dodo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("Update")
    public ResponseEntity<?> update(@RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(service.update(request));
    }
    @GetMapping("getById")
    public ResponseEntity<?> getById(Long id){
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
    public ResponseEntity<?> getUserByToken(@RequestHeader String token ){
        return ResponseEntity.ok(service.getUserByToken(token));
    }
}

