package org.springboot.finalspringboot.services;


import org.springboot.finalspringboot.model.entities.Order;
import org.springboot.finalspringboot.model.entities.OrderItem;
import org.springboot.finalspringboot.model.repotories.OrderItemRepository;
import org.springboot.finalspringboot.model.repotories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Lưu đơn hàng
    public void save(Order order) {
        orderRepository.save(order);
    }

    // Lưu order item
    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
