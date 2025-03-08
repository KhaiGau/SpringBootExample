package org.springboot.finalspringboot.model.entities;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Cart {
    private Map<Long, CartItem> items = new HashMap<>();

    // Thêm sản phẩm vào giỏ hàng
    public void addItem(Product product, int quantity) {
        if (items.containsKey(product.getId())) {
            CartItem existingItem = items.get(product.getId());
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            items.put(product.getId(), new CartItem(product, quantity));
        }
    }

    // Lấy tất cả các sản phẩm trong giỏ
    public Map<Long, CartItem> getItems() {
        return items;
    }

    // Tính tổng tiền trong giỏ
    public double getTotal() {
        double total = 0.0;
        for (CartItem item : items.values()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
    // Xóa sản phẩm khỏi giỏ hàng
    public void removeFromCart(Long productId) {
        items.remove(productId);  // Xóa sản phẩm theo productId
    }
}
