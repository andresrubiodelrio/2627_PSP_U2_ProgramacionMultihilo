package completablefuture.ejemplousers.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import completablefuture.ejemplousers.ApiException;
import completablefuture.ejemplousers.dto.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class UserService {

    private static final String API_URL =
            "https://jsonplaceholder.typicode.com/users";

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    private final ExecutorService executor;

    public UserService(ExecutorService executor) {
        this.executor = executor;
    }

    public CompletableFuture<List<User>> fetchUsersAsync() {

        return CompletableFuture
                .supplyAsync(() -> {

                    HttpURLConnection connection = null;

                    try {

                        URL url = new URL(API_URL);

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

                            throw new RuntimeException(
                                    new ApiException(
                                            "HTTP error: " +
                                                    statusCode
                                    )
                            );
                        }

                        try (InputStream inputStream =
                                     connection.getInputStream()) {

                            User[] usersArray =
                                    objectMapper.readValue(
                                            inputStream,
                                            User[].class
                                    );

                            return Arrays.asList(usersArray);
                        }

                    }
                    catch (IOException e) {

                        throw new RuntimeException(
                                new ApiException(
                                        "Error API",
                                        e
                                )
                        );
                    } finally {

                        if (connection != null) {
                            connection.disconnect();
                        }
                    }

                }, executor)

                .orTimeout(10,
                        java.util.concurrent.TimeUnit.SECONDS);
    }
}
