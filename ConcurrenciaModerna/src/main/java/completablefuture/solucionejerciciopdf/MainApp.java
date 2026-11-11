package completablefuture.solucionejerciciopdf;

import completablefuture.solucionejerciciopdf.dto.Comment;
import completablefuture.solucionejerciciopdf.dto.Post;
import completablefuture.solucionejerciciopdf.dto.User;
import completablefuture.solucionejerciciopdf.servicio.CommentService;
import completablefuture.solucionejerciciopdf.servicio.PostService;
import completablefuture.solucionejerciciopdf.servicio.UserService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MainApp {

    public static void main(String[] args) {
        ExecutorService executor =
                Executors.newFixedThreadPool(
                        Runtime
                                .getRuntime()
                                .availableProcessors()
                );

        UserService userService =
                new UserService();

        PostService postService =
                new PostService();

        CommentService commentService =
                new CommentService();


        try {

            CompletableFuture<List<User>> usersFuture =
                    CompletableFuture
                            .supplyAsync(
                                    () -> {
                                        try {
                                            return userService.fetchUsers();
                                        } catch (ApiException e) {
                                            throw new RuntimeException(e);
                                        }
                                    },
                                    executor
                            );

            CompletableFuture<List<Post>> postsFuture =
                    CompletableFuture
                            .supplyAsync(
                                    () -> {
                                        try {
                                            return postService.fetchAllPosts();
                                        } catch (ApiException e) {
                                            throw new RuntimeException(e);
                                        }
                                    },
                                    executor
                            );

            CompletableFuture<List<Comment>> commentsFuture =
                    CompletableFuture
                            .supplyAsync(
                                    () -> {
                                        try {
                                            return commentService.fetchAllComments();
                                        } catch (ApiException e) {
                                            throw new RuntimeException(e);
                                        }
                                    },
                                    executor
                            );
            CompletableFuture<Void> combined =
                    CompletableFuture
                            .allOf(
                                    usersFuture,
                                    postsFuture,
                                    commentsFuture
                            );

            combined
                    .thenRun(() -> {

                        List<User> users =
                                usersFuture.join();

                        List<Post> posts =
                                postsFuture.join();

                        List<Comment> comments =
                                commentsFuture.join();

                        Map<Integer, List<Post>> postsByUser =
                                posts.stream()
                                        .collect(
                                                Collectors
                                                        .groupingBy(
                                                                Post::getUserId
                                                        )
                                        );

                        Map<Integer, List<Comment>> commentsByPost =
                                comments.stream()
                                        .collect(
                                                Collectors
                                                        .groupingBy(
                                                                Comment::getPostId
                                                        )
                                        );

                        for (User user : users) {

                            List<Post> userPosts =
                                    postsByUser
                                            .getOrDefault(
                                                    user.getId(),
                                                    List.of()
                                            );

                            for (Post post : userPosts) {

                                post.setComments(
                                        commentsByPost
                                                .getOrDefault(
                                                        post.getId(),
                                                        List.of()
                                                )
                                );
                            }

                            user.setPosts(userPosts);
                        }

                        System.out.println(
                                "Data loaded asynchronously"
                        );

                        printData(users);

                    })
                    .exceptionally(ex -> {

                        ex.printStackTrace();

                        return null;
                    })
                    .join();

        } finally {

            executor.shutdown();
        }
    }

    private static void printData(
            List<User> users
    ) {

        if (users == null || users.isEmpty()) {

            System.out.println(
                    "No hay datos para mostrar"
            );

            return;
        }

        for (User user : users) {

            System.out.println(
                    "User: " +
                            user.getName()
            );

            if (user.getPosts() == null)
                continue;

            for (Post post : user.getPosts()) {

                System.out.println(
                        "  Post: " +
                                post.getTitle()
                );

                if (post.getComments() == null)
                    continue;

                for (Comment comment :
                        post.getComments()) {

                    System.out.println(
                            "    Comment: " +
                                    comment.getEmail()
                    );
                }
            }
        }
    }
}
