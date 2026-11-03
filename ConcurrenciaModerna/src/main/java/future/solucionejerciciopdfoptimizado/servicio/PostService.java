package future.solucionejerciciopdfoptimizado.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import future.solucionejerciciopdfoptimizado.ApiException;
import future.solucionejerciciopdfoptimizado.dto.Post;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PostService {

    private static final String URL_POSTS =
            "https://jsonplaceholder.typicode.com/posts";

    private final ObjectMapper mapper = new ObjectMapper();

    public List<Post> fetchPostsByUser(int userId)
            throws ApiException {

        try {

            HttpURLConnection connection =
                    (HttpURLConnection)
                            new URL(URL_POSTS)
                                    .openConnection();

            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {
                throw new ApiException("HTTP error posts");
            }

            try (InputStream is = connection.getInputStream()) {

                Post[] posts =
                        mapper.readValue(is, Post[].class);

                return Arrays
                        .stream(posts)
                        .filter(p -> p.getUserId() == userId)
                        .collect(Collectors.toList());
            }

        }
        catch (Exception e) {
            throw new ApiException("Error posts", e);
        }
    }

    public List<Post> fetchAllPosts() throws ApiException {

        HttpURLConnection connection = null;

        try {

            URL url = new URL(URL_POSTS);

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
                        "HTTP error posts: " +
                                statusCode
                );
            }

            try (InputStream inputStream =
                         connection.getInputStream()) {

                Post[] postsArray =
                        mapper.readValue(
                                inputStream,
                                Post[].class
                        );

                return Arrays.asList(postsArray);
            }

        }
        catch (IOException e) {

            throw new ApiException(
                    "Error fetching posts",
                    e
            );
        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
