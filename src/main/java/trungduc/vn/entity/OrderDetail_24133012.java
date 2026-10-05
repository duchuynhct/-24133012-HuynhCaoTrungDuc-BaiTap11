package trungduc.vn.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OrderDetails")
public class OrderDetail_24133012 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailId")
    private int orderDetailId;

    @ManyToOne
    @JoinColumn(name = "OrderId", nullable = false)
    private Order_24133012 order;

    @ManyToOne
    @JoinColumn(name = "VideoId", nullable = false)
    private Video_24133012 video;

    @Column(name = "Price", nullable = false)
    private double price;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    public OrderDetail_24133012() {
    }

    public OrderDetail_24133012(Order_24133012 order, Video_24133012 video, double price, int quantity) {
        this.order = order;
        this.video = video;
        this.price = price;
        this.quantity = quantity;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Order_24133012 getOrder() {
        return order;
    }

    public void setOrder(Order_24133012 order) {
        this.order = order;
    }

    public Video_24133012 getVideo() {
        return video;
    }

    public void setVideo(Video_24133012 video) {
        this.video = video;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
