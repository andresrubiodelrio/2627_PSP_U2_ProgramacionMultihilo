package future.solucionejerciciopdfoptimizado.executor;

import future.solucionejerciciopdfoptimizado.dto.Comment;
import future.solucionejerciciopdfoptimizado.servicio.CommentService;

import java.util.List;
import java.util.concurrent.Callable;

public class CommentTask implements Callable<List<Comment>> {

    private final int postId;
    private final CommentService service;

    public CommentTask(int postId,
                       CommentService service) {
        this.postId = postId;
        this.service = service;
    }

    @Override
    public List<Comment> call() throws Exception {

        return service.fetchCommentsByPost(postId);
    }
}
