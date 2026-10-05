package trungduc.vn.dao;

import java.util.List;
import trungduc.vn.entity.Order_24133012;

public interface IOrderDao_24133012 {
    void insert(Order_24133012 order);
    Order_24133012 findById(int orderId);
    List<Order_24133012> findByUser(String username);
    List<Order_24133012> findByUserAndStatus(String username, String status);
    void update(Order_24133012 order);
    void updateStatus(int orderId, String newStatus);
}
