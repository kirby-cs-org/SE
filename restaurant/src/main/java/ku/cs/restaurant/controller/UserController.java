package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.user.SigninResponse;
import ku.cs.restaurant.dto.user.UserResponse;
import ku.cs.restaurant.entity.User;
import ku.cs.restaurant.service.UserService;
import ku.cs.restaurant.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public UserController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> customers = new ArrayList<>(userService.getAllCustomers());

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        Optional<User> optionalUser = userService.getUserById(id);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/jwt")
    public UserResponse getUsernameByJwt(@RequestHeader("Authorization") String jwt) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        Optional<User> optionalUser = userService.getUserByUsername(username);
        UserResponse res = new UserResponse();
        if (optionalUser.isPresent()) {
            res.setUsername(optionalUser.get().getUsername());
            res.setId(optionalUser.get().getId());
            res.setRole(optionalUser.get().getRole());
            res.setPhone(optionalUser.get().getPhone());
        }
        return res;
    }
}

