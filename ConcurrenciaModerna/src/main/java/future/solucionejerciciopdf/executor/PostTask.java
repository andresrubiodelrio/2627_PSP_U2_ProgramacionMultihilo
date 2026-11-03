package future.solucionejerciciopdf.executor;

import future.solucionejerciciopdf.dto.Post;
import future.solucionejerciciopdf.servicio.PostService;

import java.util.List;
import java.util.concurrent.Callable;

public class PostTask implements Callable<List<Post>> {

    private final int userId;
    private final PostService service;

    public PostTask(int userId,
                    PostService service) {
        this.userId = userId;
        this.service = service;
    }

    @Override
    public List<Post> call() throws Exception {

        return service.fetchPostsByUser(userId);
    }
}
