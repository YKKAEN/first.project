package my.first.project.controllers;

import my.first.project.entities.Comments;
import my.first.project.services.CommentService;
import my.first.project.services.ProductService;
import my.first.project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String addComment(@RequestParam(name = "productId") Long productId,
                             @RequestParam(name = "comment") String comment) {
        Comments comments = new Comments();
        comments.setComment(comment);
        commentService.addComment(comments, productId);
        return "redirect:/productDetails/" + productId;
    }

    @PostMapping(value = "/delete")
    public String deleteComment(@RequestParam(name = "comment_id") Long id) {
        commentService.deleteCommentById(id);
        return "redirect:/productDetails";
    }
}
