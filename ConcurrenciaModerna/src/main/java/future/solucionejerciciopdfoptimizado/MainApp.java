package future.solucionejerciciopdfoptimizado;

import future.solucionejerciciopdfoptimizado.dto.Comment;
import future.solucionejerciciopdfoptimizado.dto.Post;
import future.solucionejerciciopdfoptimizado.dto.User;
import future.solucionejerciciopdfoptimizado.servicio.CommentService;
import future.solucionejerciciopdfoptimizado.servicio.PostService;
import future.solucionejerciciopdfoptimizado.servicio.UserService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainApp {
    public static void main(String[] args)
            throws Exception {

        int threads =
                Runtime
                        .getRuntime()
                        .availableProcessors();

        ExecutorService executor =
                Executors.newFixedThreadPool(threads);

        UserService userService =
                new UserService();

        PostService postService =
                new PostService();

        CommentService commentService =
                new CommentService();

        try {

            Future<List<User>> usersFuture = executor.submit(userService::fetchUsers);

            Future<List<Post>> postsFuture = executor.submit(postService::fetchAllPosts);

            Future<List<Comment>> commentsFuture =executor.submit(commentService::fetchAllComments);

            List<User> users = usersFuture.get(10,TimeUnit.SECONDS);

            List<Post> posts =postsFuture.get(10,TimeUnit.SECONDS);

            List<Comment> comments =commentsFuture.get(10,TimeUnit.SECONDS);

            Map<Integer, List<Post>> postsByUser = posts.stream()
                            .collect(Collectors.groupingBy(Post::getUserId));

            Map<Integer, List<Comment>> commentsByPost =comments.stream()
                            .collect(Collectors.groupingBy(Comment::getPostId));
            for (User user : users) {

                List<Post> userPosts =postsByUser.getOrDefault(user.getId(),List.of());

                for (Post post : userPosts) {

                    post.setComments(commentsByPost.getOrDefault(post.getId(),List.of()));
                }

                user.setPosts(userPosts);

                printData(users);
            }

        }
        finally {

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
