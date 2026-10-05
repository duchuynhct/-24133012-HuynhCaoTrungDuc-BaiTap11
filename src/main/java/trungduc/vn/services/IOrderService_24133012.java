package trungduc.vn.services;

import java.util.Collection;
import java.util.List;

import trungduc.vn.entity.Order_24133012;
import trungduc.vn.entity.User_24133012;
import trungduc.vn.models.CartItem_24133012;

public interface IOrderService_24133012 {
    Order_24133012 createOrder(User_24133012 user, String fullname, String phone, String address, String note, Collection<CartItem_24133012> items);
    Order_24133012 findById(int orderId);
    List<Order_24133012> findByUser(String username);
    List<Order_24133012> findByUserAndStatus(String username, String status);
    void updateStatus(int orderId, String newStatus);
}
