package future.solucionejerciciopdf;

import future.solucionejerciciopdf.dto.Comment;
import future.solucionejerciciopdf.dto.Post;
import future.solucionejerciciopdf.dto.User;
import future.solucionejerciciopdf.executor.CommentTask;
import future.solucionejerciciopdf.executor.PostTask;
import future.solucionejerciciopdf.servicio.CommentService;
import future.solucionejerciciopdf.servicio.PostService;
import future.solucionejerciciopdf.servicio.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainApp {

    public static void main(String[] args) {

        ExecutorService executor =
                Executors.newFixedThreadPool(10);


        UserService userService =
                new UserService();

        PostService postService =
                new PostService();

        CommentService commentService =
                new CommentService();


        try {

            List<User> users =userService.fetchUsers();

            List<Future<List<Post>>> postFutures = new ArrayList<>();

            for (User user : users) {
                Future<List<Post>> future =executor.submit(new PostTask(user.getId(),postService));

                postFutures.add(future);
            }


            for (int i = 0; i < users.size(); i++) {

                List<Post> posts =postFutures.get(i).get();

                users.get(i).setPosts(posts);

                List<Future<List<Comment>>> commentFutures = new ArrayList<>();

                for (Post post : posts) {

                    Future<List<Comment>> future = executor.submit(new CommentTask(post.getId(),commentService));

                    commentFutures.add(future);
                }

                for (int j = 0; j < posts.size();j++) {

                    posts.get(j).setComments(commentFutures.get(j).get());
                }
            }

            printData(users);

        }
        catch (Exception e) {

            e.printStackTrace();
        }
        finally {

            executor.shutdown();
        }
    }

    private static void printData(
            List<User> users
    ) {

        for (User user : users) {

            System.out.println(
                    "User: " +
                            user.getName()
            );

            for (Post post : user.getPosts()) {

                System.out.println(
                        "  Post: " +
                                post.getTitle()
                );

                post.getComments()
                        .forEach(comment ->
                                System.out.println(
                                        "    Comment: " +
                                                comment
                                                        .getEmail()
                                )
                        );
            }
        }
    }
}