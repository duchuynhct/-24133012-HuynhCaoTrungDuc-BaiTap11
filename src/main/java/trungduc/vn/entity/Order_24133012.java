package trungduc.vn.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Orders")
public class Order_24133012 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "Username", nullable = false)
    private User_24133012 user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OrderDate", nullable = false)
    private Date orderDate;

    @Column(name = "Fullname", length = 100, nullable = false)
    private String fullname;

    @Column(name = "Phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "Address", length = 500, nullable = false)
    private String address;

    @Column(name = "Note", length = 500)
    private String note;

    @Column(name = "TotalAmount", nullable = false)
    private double totalAmount;

    @Column(name = "PaymentMethod", length = 50, nullable = false)
    private String paymentMethod = "COD";

    @Column(name = "Status", length = 50, nullable = false)
    private String status = "Đơn hàng mới";

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetail_24133012> orderDetails;

    public Order_24133012() {
        this.orderDate = new Date();
    }

    public Order_24133012(User_24133012 user, Date orderDate, String fullname, String phone, String address,
            String note, double totalAmount, String paymentMethod, String status) {
        this.user = user;
        this.orderDate = orderDate;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.note = note;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User_24133012 getUser() {
        return user;
    }

    public void setUser(User_24133012 user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderDetail_24133012> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail_24133012> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
