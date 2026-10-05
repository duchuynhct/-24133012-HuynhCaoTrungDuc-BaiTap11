package trungduc.vn.models;

import java.io.Serializable;
import trungduc.vn.entity.Video_24133012;

public class CartItem_24133012 implements Serializable {

    private static final long serialVersionUID = 1L;

    private Video_24133012 video;
    private int quantity;
    private double price;

    public CartItem_24133012() {
    }

    public CartItem_24133012(Video_24133012 video, int quantity, double price) {
        this.video = video;
        this.quantity = quantity;
        this.price = price;
    }

    public Video_24133012 getVideo() {
        return video;
    }

    public void setVideo(Video_24133012 video) {
        this.video = video;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        // Giới hạn số lượng trong khoảng 1 đến 99
        if (quantity < 1) {
            this.quantity = 1;
        } else if (quantity > 99) {
            this.quantity = 99;
        } else {
            this.quantity = quantity;
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSubTotal() {
        return this.price * this.quantity;
    }
}
