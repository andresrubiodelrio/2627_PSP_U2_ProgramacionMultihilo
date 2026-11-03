package future.ejemplousers.executor;

import future.ejemplousers.ApiException;
import future.ejemplousers.dto.User;
import future.ejemplousers.servicio.UserService;

import java.util.List;
import java.util.concurrent.Callable;

/*
Responsabilidades:

Ejecutar la llamada al servicio

Retornar una lista de usuarios

Permitir ejecución en paralelo
 */

public class UserTask implements Callable<List<User>> {

    private final UserService userService;

    public UserTask(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<User> call() throws ApiException {

        System.out.println(
                "Ejecutando tarea en hilo: " +
                        Thread.currentThread().getName()
        );

        return userService.fetchUsers();
    }
}
