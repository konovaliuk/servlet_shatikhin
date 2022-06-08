package project.service;

import project.dao.DAOFactory;
import project.entities.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    public Product getProduct(int id) {
        Product prod;
        try {
            prod = DAOFactory.getProductDAO().getProduct(id);
        } catch (SQLException e) {
            return null;
        }
        return prod;
    }
    public List<Product> getProducts() {
        try {
            return DAOFactory.getProductDAO().getProducts();
        } catch (SQLException e) {
            return null;
        }
    }
}
