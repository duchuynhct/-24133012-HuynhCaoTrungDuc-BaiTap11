package trungduc.vn.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import trungduc.vn.dao.IOrderDao_24133012;
import trungduc.vn.dao.OrderDaoImpl_24133012;
import trungduc.vn.entity.OrderDetail_24133012;
import trungduc.vn.entity.Order_24133012;
import trungduc.vn.entity.User_24133012;
import trungduc.vn.models.CartItem_24133012;

public class OrderServiceImpl_24133012 implements IOrderService_24133012 {

    private IOrderDao_24133012 orderDao = new OrderDaoImpl_24133012();

    @Override
    public Order_24133012 createOrder(User_24133012 user, String fullname, String phone, String address, String note,
            Collection<CartItem_24133012> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }

        double totalAmount = 0.0;
        for (CartItem_24133012 item : items) {
            totalAmount += item.getSubTotal();
        }

        Order_24133012 order = new Order_24133012();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setFullname(fullname);
        order.setPhone(phone);
        order.setAddress(address);
        order.setNote(note);
        order.setTotalAmount(totalAmount);
        order.setPaymentMethod("COD");
        order.setStatus("Đơn hàng mới"); // Trạng thái ban đầu

        List<OrderDetail_24133012> details = new ArrayList<>();
        for (CartItem_24133012 item : items) {
            OrderDetail_24133012 detail = new OrderDetail_24133012();
            detail.setOrder(order);
            detail.setVideo(item.getVideo());
            detail.setPrice(item.getPrice());
            detail.setQuantity(item.getQuantity());
            details.add(detail);
        }
        order.setOrderDetails(details);

        orderDao.insert(order);
        return order;
    }

    @Override
    public Order_24133012 findById(int orderId) {
        return orderDao.findById(orderId);
    }

    @Override
    public List<Order_24133012> findByUser(String username) {
        return orderDao.findByUser(username);
    }

    @Override
    public List<Order_24133012> findByUserAndStatus(String username, String status) {
        if (status == null || status.trim().isEmpty() || "ALL".equalsIgnoreCase(status) || "Tất cả".equalsIgnoreCase(status)) {
            return orderDao.findByUser(username);
        }
        return orderDao.findByUserAndStatus(username, status.trim());
    }

    @Override
    public void updateStatus(int orderId, String newStatus) {
        orderDao.updateStatus(orderId, newStatus);
    }
}
