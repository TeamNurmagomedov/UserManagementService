package miniyoutube.com.usermanagementservice.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    HttpSession session;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "register")
    public void register(@RequestBody RegisterDTO registerDTO) {
        userService.insertUser(registerDTO);
    }

    @PostMapping(path = "login")
    public ResponseEntity login(@RequestBody UserDTO userDTO) {
        try {
            String id = userService.Login(userDTO);
            if (id != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Set-Cookie", id);
                session.setAttribute("token", id);
                return ResponseEntity.status(HttpStatus.OK).headers(headers).body("Welcome " + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid credentials");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), e.getMessage());
        }
    }

    @GetMapping(path = "email")
    public String getEmail(@RequestBody String id) {
        return userService.getEmail(id);
    }

    @PostMapping(path = "logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body("Logout successful");
    }

}
