package org.springboot.finalspringboot.model.repotories;


import org.springboot.finalspringboot.model.entities.Order;
import org.springboot.finalspringboot.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(Users user);
}

