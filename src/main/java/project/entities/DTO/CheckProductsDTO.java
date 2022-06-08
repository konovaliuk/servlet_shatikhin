package project.entities.DTO;

import project.entities.Product;
import project.manager.Helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckProductsDTO {
    private Product product;
    private float quantity;

    public CheckProductsDTO(Product product, float quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        double cost = this.product.getPrice() * this.quantity;
        return Helper.round(cost, 2);
    }
}
