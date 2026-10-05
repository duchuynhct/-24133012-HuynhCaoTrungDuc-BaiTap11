package trungduc.vn.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CartItem_24133012Test {

    @Test
    void setQuantityClampsValuesBelowMinimum() {
        CartItem_24133012 item = new CartItem_24133012();

        item.setQuantity(0);

        assertEquals(1, item.getQuantity());
    }

    @Test
    void setQuantityClampsValuesAboveMaximum() {
        CartItem_24133012 item = new CartItem_24133012();

        item.setQuantity(100);

        assertEquals(99, item.getQuantity());
    }

    @Test
    void setQuantityKeepsValuesWithinBounds() {
        CartItem_24133012 item = new CartItem_24133012();

        item.setQuantity(25);

        assertEquals(25, item.getQuantity());
    }

    @Test
    void subTotalMultipliesUnitPriceByQuantity() {
        CartItem_24133012 item = new CartItem_24133012(null, 3, 12.5);

        assertEquals(37.5, item.getSubTotal(), 0.0001);
    }
}
