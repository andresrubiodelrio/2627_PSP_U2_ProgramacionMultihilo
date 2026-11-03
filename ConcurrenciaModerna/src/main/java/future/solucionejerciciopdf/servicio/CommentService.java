package future.solucionejerciciopdf.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import future.solucionejerciciopdf.ApiException;
import future.solucionejerciciopdf.dto.Comment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommentService {

    private static final String URL_COMMENTS =
            "https://jsonplaceholder.typicode.com/comments";

    private final ObjectMapper mapper = new ObjectMapper();

    public List<Comment> fetchCommentsByPost(int postId)
            throws ApiException {

        try {

            HttpURLConnection connection =
                    (HttpURLConnection)
                            new URL(URL_COMMENTS)
                                    .openConnection();

            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {
                throw new ApiException("HTTP error comments");
            }

            try (InputStream is = connection.getInputStream()) {

                Comment[] comments =
                        mapper.readValue(is, Comment[].class);

                return Arrays
                        .stream(comments)
                        .filter(c -> c.getPostId() == postId)
                        .collect(Collectors.toList());
            }

        }
        catch (Exception e) {
            throw new ApiException("Error comments", e);
        }
    }
}
