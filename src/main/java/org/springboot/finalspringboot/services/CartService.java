package org.springboot.finalspringboot.services;

import org.springboot.finalspringboot.model.entities.Cart;
import org.springboot.finalspringboot.model.entities.Product;
import org.springboot.finalspringboot.model.repotories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

@Service
public class CartService {
    @Autowired
    ProductRepository productRepository;

    public Cart getCart(WebRequest request) {
        Cart cart = (Cart) request.getAttribute("cart", WebRequest.SCOPE_SESSION);
        if (cart == null) {
            cart = new Cart();
            request.setAttribute("cart", cart, WebRequest.SCOPE_SESSION);
        }
        return cart;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(WebRequest request, Long productId, int quantity) {
        Cart cart = getCart(request);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        cart.addItem(product, quantity);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeFromCart(WebRequest request, Long productId) {
        Cart cart = getCart(request);
        cart.removeFromCart(productId);
    }

    // Tính tổng giỏ hàng
    public double getTotal(WebRequest request) {
        Cart cart = getCart(request);
        return cart.getTotal();
    }

    public void clearCart(WebRequest request) {
        // Ví dụ nếu giỏ hàng được lưu trong session:
        request.removeAttribute("cart", WebRequest.SCOPE_SESSION);
    }
}

