package miniyoutube.com.usermanagementservice.controllers;

import miniyoutube.com.usermanagementservice.dtos.UserDTO;
import miniyoutube.com.usermanagementservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void insertUser(@RequestBody UserDTO userDTO) {
        userService.insertUser(userDTO);
    }
}
