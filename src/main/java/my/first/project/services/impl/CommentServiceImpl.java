package my.first.project.services.impl;

import my.first.project.entities.Comments;
import my.first.project.entities.Product;
import my.first.project.entities.Users;
import my.first.project.repositories.CommentRepository;
import my.first.project.services.CommentService;
import my.first.project.services.ProductService;
import my.first.project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private ProductService productService;
    @Override
    public List<Comments> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comments addComment(Comments comments,Long productId) {
        Users users = usersService.getUserData();
        Product product = productService.getProductById(productId);
        comments.setProduct(product);
        comments.setUsers(users);
        return commentRepository.save(comments);
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }
}

