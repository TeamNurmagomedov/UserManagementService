package miniyoutube.com.usermanagementservice.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity(name = "Users")
public class User {

    @Id
    private String Id = UUID.randomUUID().toString();
    @Column(
            name = "Username",
            nullable = false
    )
    private String username;
    @Column(
            name = "Email",
            nullable = false
    )
    private String email;
    @Column(
            name = "Password",
            nullable = false
    )
    private String password;

    public User(String username, String email, String password)
    {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User()
    {
    }

    public String getId()
    {
        return Id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
