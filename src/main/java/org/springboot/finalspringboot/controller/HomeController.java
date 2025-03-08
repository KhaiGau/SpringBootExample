package org.springboot.finalspringboot.controller;

import org.springboot.finalspringboot.model.entities.*;
import org.springboot.finalspringboot.model.repotories.UserRepository;
import org.springboot.finalspringboot.services.CartService;
import org.springboot.finalspringboot.services.OrderService;
import org.springboot.finalspringboot.services.ProductService;
import org.springboot.finalspringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String home(Model model,  WebRequest request) {
        Cart cart = cartService.getCart(request);
        List<Product> products = productService.findAll();

        boolean hasItemsInCart = !cart.getItems().isEmpty();
        model.addAttribute("cart", cart);
        model.addAttribute("hasItemsInCart", hasItemsInCart);

        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String keyword, Model model)
    {
        List<Product> result = productService.searchProduct(keyword);
        model.addAttribute("products", result);
        return "home";
    }


    @GetMapping("/addToCart")
    public String addToCart(@RequestParam Long productId, @RequestParam(defaultValue = "1") int quantity, WebRequest request, Model model) {
        cartService.addToCart(request, productId, quantity);
        return "redirect:/cart";
    }

    // Hiển thị giỏ hàng
    @GetMapping("/cart")
    public String viewCart(WebRequest request, Model model) {
        Cart cart = cartService.getCart(request);
        double total = cartService.getTotal(request);
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";  // Hiển thị trang giỏ hàng
    }

    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("productId") Long productId, WebRequest request) {
        cartService.removeFromCart(request, productId);
        return "redirect:/cart";
    }
    @GetMapping("/checkout")
    public String checkout(WebRequest request, Model model) {
        Cart cart = cartService.getCart(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userService.findByUsername(username);
        Order order = new Order();
        order.setUser(user);
        order.setStatus("Đang xử lý");
        orderService.save(order);
        Map<Long, CartItem> cartItemsMap = cart.getItems();

        // Chuyển đổi Map thành List
        List<CartItem> items = new ArrayList<>(cartItemsMap.values());

        for (CartItem item : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderService.saveOrderItem(orderItem);
        }
        cartService.clearCart(request);
        model.addAttribute("message", "Order successfully placed");

        return "success";
    }
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);

        return "Login";
    }

    @PostMapping("/logout")
    public String logout(Model model) {
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") Users user, BindingResult bindingResult, Model model) {

        // Kiểm tra nếu email đã tồn tại
        if (userService.findByUsername(user.getEmail()) != null) {
            model.addAttribute("emailError", "Email already exists!");
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }
}
