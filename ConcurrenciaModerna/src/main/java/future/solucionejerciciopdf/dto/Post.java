package future.solucionejerciciopdf.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

    private int userId;
    private int id;
    private String title;
    private String body;

    private List<Comment> comments = new ArrayList<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId<0)
            throw new IllegalArgumentException("ERROR: El userId no puede ser negativo.");

        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id<0)
            throw new IllegalArgumentException("ERROR: El id de un Post no puede ser negativo.");

        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Objects.requireNonNull(title,"ERROR: El título de un post no puede ser nulo.");

        if (title.isBlank())
            throw new IllegalArgumentException("ERROR: El título de un post no puede ser vacío.");

        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        Objects.requireNonNull(body,"ERROR: El body de un post no puede ser nulo.");

        if (body.isBlank())
            throw new IllegalArgumentException("ERROR: El body de un post no puede ser vacío.");

        this.body = body;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
