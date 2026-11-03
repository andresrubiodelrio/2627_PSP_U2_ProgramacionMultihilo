package future.ejemplousers.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import future.ejemplousers.ApiException;
import future.ejemplousers.dto.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
/*
Responsabilidades:

Realizar la solicitud HTTP

Validar el código de respuesta

Convertir JSON a objetos Java

Manejar excepciones de red y parsing
 */

public class UserService {

    private static final String API_URL =
            "https://jsonplaceholder.typicode.com/users";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<User> fetchUsers() throws ApiException {

        HttpURLConnection connection = null;

        try {

            URL url = new URL(API_URL);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int statusCode = connection.getResponseCode();

            if (statusCode != HttpURLConnection.HTTP_OK) {
                throw new ApiException(
                        "Error en respuesta HTTP: " + statusCode
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

        } catch (IOException e) {

            throw new ApiException(
                    "Error al comunicarse con la API",
                    e
            );

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
