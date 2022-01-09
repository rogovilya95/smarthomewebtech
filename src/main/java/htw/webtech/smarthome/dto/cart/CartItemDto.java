package htw.webtech.smarthome.dto.cart;

import htw.webtech.smarthome.domain.Cart;
import htw.webtech.smarthome.domain.Product;

public class CartItemDto {

    private long id;
    private int quantity;
    private Product product;

    public CartItemDto(Cart cart) {
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
