package future.solucionejerciciopdfoptimizado.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import future.solucionejerciciopdfoptimizado.ApiException;
import future.solucionejerciciopdfoptimizado.dto.Comment;

import java.io.IOException;
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

    public List<Comment> fetchAllComments() throws ApiException {

        HttpURLConnection connection = null;

        try {

            URL url = new URL(URL_COMMENTS);

            connection =
                    (HttpURLConnection)
                            url.openConnection();

            connection.setRequestMethod("GET");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int statusCode =
                    connection.getResponseCode();

            if (statusCode !=
                    HttpURLConnection.HTTP_OK) {

                throw new ApiException(
                        "HTTP error comments: " +
                                statusCode
                );
            }

            try (InputStream inputStream =
                         connection.getInputStream()) {

                Comment[] commentsArray =
                        mapper.readValue(
                                inputStream,
                                Comment[].class
                        );

                return Arrays.asList(commentsArray);
            }

        }
        catch (IOException e) {

            throw new ApiException(
                    "Error fetching comments",
                    e
            );
        }
        finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
