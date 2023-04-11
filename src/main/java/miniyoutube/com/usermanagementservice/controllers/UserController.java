package miniyoutube.com.usermanagementservice.controllers;

import miniyoutube.com.usermanagementservice.dtos.RegisterDTO;
import miniyoutube.com.usermanagementservice.dtos.UserDTO;
import miniyoutube.com.usermanagementservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "register")
    public void register(@RequestBody RegisterDTO registerDTO) {
        userService.insertUser(registerDTO);
    }

    @PostMapping(path = "login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try {
            String id = userService.Login(userDTO);
            if (id != null) {
                ResponseCookie springCookie = ResponseCookie.from("user-id", id)
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .maxAge(86400) // 1 day in seconds
                        .domain("localhost")
                        .build();
                return ResponseEntity
                        .ok()
                        .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                        .build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid credentials.");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), e.getMessage());
        }
    }

    @GetMapping(path = "email")
    public String getEmail(@CookieValue("user-id") String id) {
        return userService.getEmail(id);
    }

    @PostMapping(path = "logout")
    public ResponseEntity<?> logout() {
        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("user-id", null)
                .build();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                .build();
    }

}
