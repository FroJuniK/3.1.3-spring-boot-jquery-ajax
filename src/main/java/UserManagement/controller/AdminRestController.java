package UserManagement.controller;

import UserManagement.dto.UserDto;
import UserManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest/admin")
public class AdminRestController {

    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<String>> getAllRoles() {
        return new ResponseEntity<>(service.getNameRoles(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) {
        service.addUser(user);
        return new ResponseEntity<>(service.getUserByName(user.getName()), HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto user) {
        service.editUser(user);
        return new ResponseEntity<>(service.getUserByName(user.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        service.deleteUser(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
