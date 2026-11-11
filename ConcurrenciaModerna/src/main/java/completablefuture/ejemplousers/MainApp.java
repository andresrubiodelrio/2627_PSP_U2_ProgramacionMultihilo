package completablefuture.ejemplousers;

import completablefuture.ejemplousers.dto.User;
import completablefuture.ejemplousers.servicio.UserService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApp {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        UserService service = new UserService(executor);

        try {

            CompletableFuture<List<User>> f1 = service.fetchUsersAsync();

            CompletableFuture<List<User>> f2 = service.fetchUsersAsync();

            CompletableFuture<List<User>> f3 = service.fetchUsersAsync();

            CompletableFuture.allOf(f1, f2, f3)

                    .thenRun(() -> {

                        System.out.println(
                                "Todas las tareas terminadas"
                        );
                    })

                    .exceptionally(ex -> {

                        System.err.println(
                                "Error global"
                        );

                        ex.printStackTrace();

                        return null;
                    })

                    .join();

            f1.thenAccept(MainApp::printUsers);
            f2.thenAccept(MainApp::printUsers);
            f3.thenAccept(MainApp::printUsers);

        }
        finally {

            shutdown(executor);
        }
    }

    private static void printUsers(
            List<User> users
    ) {

        users.forEach(System.out::println);
    }

    private static void shutdown(
            ExecutorService executor
    ) {

        executor.shutdown();
    }
}
