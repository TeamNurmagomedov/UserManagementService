package miniyoutube.com.usermanagementservice.services;

import miniyoutube.com.usermanagementservice.dtos.RegisterDTO;
import miniyoutube.com.usermanagementservice.dtos.UserDTO;
import miniyoutube.com.usermanagementservice.models.User;
import miniyoutube.com.usermanagementservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insertUser(RegisterDTO registerDTO) {
        String hashedPassword = passwordEncoder.encode(registerDTO.getPassword());
        User user = new User(registerDTO.getUsername(), registerDTO.getEmail(), hashedPassword);
        userRepository.findByUsername(registerDTO.getUsername()).ifPresent(u -> { // if Present return true object is found
            throw new IllegalStateException("Username already exists.");
        });
        userRepository.save(user);
    }

    public String Login(UserDTO userDTO) {
        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());
        if (user.isPresent()) {
            if (passwordEncoder.matches(userDTO.getPassword(), user.get().getPassword())) {
                return user.get().getId();
            } else {
                throw new IllegalArgumentException("Password is incorrect.");
            }
        } else {
            throw new IllegalArgumentException("Username is incorrect.");
        }
    }

    public String getEmail(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get().getEmail();
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

    public String getUsername(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get().getUsername();
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

}
