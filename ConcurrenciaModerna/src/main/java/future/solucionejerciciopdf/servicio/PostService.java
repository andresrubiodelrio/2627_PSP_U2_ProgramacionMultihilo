package future.solucionejerciciopdf.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import future.solucionejerciciopdf.ApiException;
import future.solucionejerciciopdf.dto.Post;

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
}
