package future.ejemplousers;

import future.ejemplousers.dto.User;
import future.ejemplousers.executor.UserTask;
import future.ejemplousers.servicio.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
Responsabilidades:

Crear el ExecutorService

Enviar tareas concurrentes

Obtener resultados mediante Future

Manejar todas las excepciones posibles

Cerrar correctamente el pool de hilos

Flujo de Ejecución

Se crea un pool de hilos mediante:

Executors.newFixedThreadPool

Se envían tareas concurrentes:

executor.submit(task)

Cada tarea:

UserTask -> UserService -> API REST

El resultado se obtiene mediante:

Future.get()

El Executor se cierra correctamente.
 */
public class MainApp {

    public static void main(String[] args) {

        ExecutorService executor =Executors.newFixedThreadPool(3);

        List<Future<List<User>>> futures = new ArrayList<>();

        UserService userService =new UserService();

        try {

            for (int i = 0; i < 3; i++) {

                Callable<List<User>> task =  new UserTask(userService);

                Future<List<User>> future = executor.submit(task);

                futures.add(future);
            }

            for (Future<List<User>> future : futures) {

                try {

                    List<User> users =future.get(10,TimeUnit.SECONDS);

                    users.forEach(System.out::println);

                } catch (InterruptedException e) {

                    System.err.println(
                            "El hilo fue interrumpido"
                    );

                    Thread.currentThread().interrupt();
                }
                catch (ExecutionException e) {

                    System.err.println(
                            "Error durante la ejecución"
                    );

                    e.getCause().printStackTrace();
                }
                catch (TimeoutException e) {

                    System.err.println(
                            "La tarea excedió el tiempo"
                    );
                }
            }

        } finally {

            shutdownExecutor(executor);
        }
    }

    private static void shutdownExecutor(
            ExecutorService executor
    ) {

        executor.shutdown();

        try {

            if (!executor.awaitTermination(
                    5,
                    TimeUnit.SECONDS
            )) {

                executor.shutdownNow();
            }

        } catch (InterruptedException e) {

            executor.shutdownNow();

            Thread.currentThread().interrupt();
        }
    }
}

