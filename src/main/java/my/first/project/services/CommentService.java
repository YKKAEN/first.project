package my.first.project.services;

import my.first.project.entities.Comments;

import java.util.List;

public interface CommentService {

    List<Comments> getAllComments();

    Comments addComment(Comments comments, Long productId);

    void deleteCommentById(Long id);

}