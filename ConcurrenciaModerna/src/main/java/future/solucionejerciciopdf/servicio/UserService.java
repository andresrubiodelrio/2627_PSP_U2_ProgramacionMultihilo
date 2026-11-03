package future.solucionejerciciopdf.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import future.solucionejerciciopdf.ApiException;
import future.solucionejerciciopdf.dto.User;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/*
Cada servicio se encarga de:

Consumir la API
Manejar errores HTTP
Parsear JSON
Devolver listas de objetos
 */
public class UserService {

    private static final String URL_USERS =
            "https://jsonplaceholder.typicode.com/users";

    private final ObjectMapper mapper = new ObjectMapper();

    public List<User> fetchUsers() throws ApiException {

        try {

            HttpURLConnection connection =
                    (HttpURLConnection)
                            new URL(URL_USERS)
                                    .openConnection();

            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {
                throw new ApiException("HTTP error users");
            }

            try (InputStream is = connection.getInputStream()) {

                User[] users =
                        mapper.readValue(is, User[].class);

                return Arrays.asList(users);
            }

        }
        catch (Exception e) {
            throw new ApiException("Error users", e);
        }
    }
}
