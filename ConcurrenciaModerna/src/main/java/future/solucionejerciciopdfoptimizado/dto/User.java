package future.solucionejerciciopdfoptimizado.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private int id;
    private String name;
    private String username;
    private String email;
    private List<Post> posts = new ArrayList<>();


    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id<=0)
            throw new IllegalArgumentException("ERROR: El id de un usuario no puede ser negativo.");

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name,"ERROR: El nombre de un usuario no puede ser nulo.");

        if (name.isBlank())
            throw new IllegalArgumentException("ERROR: El nombre de un usuario no puede ser vacío.");

        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Objects.requireNonNull(username,"ERROR: El userName no puede ser nulo.");

        if (username.isBlank())
            throw new IllegalArgumentException("ERROR: El userName no puede ser vacío.");
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.isBlank())
            throw new IllegalArgumentException("ERROR: El email de un usuario no puede ser vacío.");

        this.email = email;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
