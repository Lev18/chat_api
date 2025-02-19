package main.java.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "app_user")
public class AppUser extends PanacheEntity {
    public String username;
    public String password;
    
    @ElementCollection
    public Set<String> roles;

    public static AppUser findByUsername(String username) {
        return find("username", username).firstResult();
    }
 }
