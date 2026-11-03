package future.solucionejerciciopdfoptimizado.dto;

import java.util.Objects;

public class Comment {

    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        if (postId<0)
            throw new IllegalArgumentException("ERRO: El postId no puede ser negativo.");

        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id<0)
            throw new IllegalArgumentException("ERROR: El id de un comentario no puede ser negativo.");

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name,"ERROR: El nombre de un comentario no puede ser nulo.");

        if (name.isBlank())
            throw new IllegalArgumentException("ERROR: El nombre de un comentario no puede ser vacío.");

        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Objects.requireNonNull(email,"ERROR: El email de un comentario no puede ser nulo.");

        if (email.isBlank())
            throw new IllegalArgumentException("ERROR: El email de un comentario no puede ser vacío.");

        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        Objects.requireNonNull(body,"ERROR: El body de un comentario no puede ser nulo.");

        if (body.isBlank())
            throw new IllegalArgumentException("ERROR: El body de un comentario no puede ser vacío.");

        this.body = body;
    }
}
