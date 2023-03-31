package my.first.project.controllers;

import my.first.project.services.ProductService;
import my.first.project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CartController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/cart")
    public String shoppingCart(Model model) {
        model.addAttribute("currentUser", usersService.getUserData());
        return "cart";
    }
}
