package miniyoutube.com.usermanagementservice.services;

import miniyoutube.com.usermanagementservice.dtos.UserDTO;
import miniyoutube.com.usermanagementservice.models.User;
import miniyoutube.com.usermanagementservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insertUser(UserDTO userDTO) {
        User user = new User(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword());
        userRepository.findAll().forEach(u -> {
            if (u.getUsername().equals(userDTO.getUsername())) {
                throw new IllegalStateException("Username already exists");
            }
        });
        userRepository.save(user);
    }

    public void deleteUser(String username) {
        userRepository.findAll().forEach(u -> {
            if (u.getUsername().equals(username)) {
                userRepository.delete(u);
                return;
            }
        });
        throw new IllegalStateException("Username does not exist");
    }
}
