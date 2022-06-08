package project.service;

import project.dao.DAOFactory;
import project.entities.*;
import project.entities.DTO.CheckProductsDTO;
import project.entities.enums.CheckStatus;
import project.manager.Helper;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CheckService {
    public List<Check> getCashierChecks (User user) {
        List<Check> checks = new ArrayList<>();
        try {
            checks = DAOFactory.getCheckDAO().getCashierChecks(user);
        } catch (SQLException e) {
            return null;
        }
        return checks;
    }
    public List<Check> getAllChecks() {
        try {
            return DAOFactory.getCheckDAO().getAllChecks();
        } catch (SQLException e) {
            return null;
        }
    }
    public Check getCheck(int check_id) {
        try {
            return DAOFactory.getCheckDAO().getCheck(check_id);
        } catch (SQLException e) {
            return null;
        }
    }
    public List<Check> getChecks(CheckStatus status) {
        try {
            return DAOFactory.getCheckDAO().getChecks(status);
        } catch (SQLException e) {
            return null;
        }
    }
    public int createCheck(int user_id) {
        Timestamp time = Timestamp.from(Instant.now());
        CheckStatus status = CheckStatus.OPENED;
        double cost = 0;
        int cashier_id = user_id;
        Check check = new Check(time, status, cost, cashier_id);
        try {
            DAOFactory.getCheckDAO().createCheck(check);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return check.getId();
    }
    public CheckProductsDTO constuctCheckProductDTO(CheckProduct checkProduct) {
        ProductService productService = new ProductService();
        Product product = productService.getProduct(checkProduct.getProduct_id());
        return new CheckProductsDTO(product, checkProduct.getQuantity());
    }
    public List<CheckProductsDTO> constructCheckProductsDTO(int check_id) {
        List<CheckProductsDTO> dto = new ArrayList<>();
        List<CheckProduct> products = getCheckProducts(check_id);
        for (CheckProduct prod : products) {
            dto.add(constuctCheckProductDTO(prod));
        }
        return dto;
    }
    public List<CheckProduct> getCheckProducts(int check_id) {
        List<CheckProduct> products = new ArrayList<>();
        try {
            products = DAOFactory.getCheckDAO().checkGetProducts(check_id);
        } catch (SQLException e) {
            return null;
        }
        return products;
    }
    public CheckProduct getCheckProduct(int check_id, int product_id) {
        CheckProduct checkProduct;
        try {
            checkProduct = DAOFactory.getCheckDAO().getCheckProduct(check_id, product_id);
        } catch (SQLException e) {
            return null;
        }
        return checkProduct;
    }
    public boolean checkSetProduct(int check_id, int product_id, float qty) {
        try {
            DAOFactory.getCheckDAO().checkSetProduct(check_id, product_id, qty);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkRemoveProduct(int check_id, int product_id) {
        try {
            DAOFactory.getCheckDAO().checkRemoveProduct(check_id, product_id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public double checkGetTotal(int check_id) {
        double total = 0;
        try {
            total = DAOFactory.getCheckDAO().checkGetTotal(check_id);
            return Helper.round(total, 2);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public List<Product> getAvailableProducts(int check_id) {
        try {
            List<Product> allProducts = DAOFactory.getProductDAO().getProducts();
            List<CheckProductsDTO> productsInCheckDTO = constructCheckProductsDTO(check_id);
            List<Product> productsInCheck = new ArrayList<>();
            for (CheckProductsDTO cpdto : productsInCheckDTO) {
                productsInCheck.add(cpdto.getProduct());
            }
            List<Product> result = new ArrayList<>();
            for (Product p : allProducts) {
                if (!productsInCheck.contains(p)) {
                    result.add(p);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean checkAddProduct(int check_id, int product_id, float quantity) {
        try {
            DAOFactory.getCheckDAO().checkAddProduct(check_id, product_id, quantity);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkEditStatus(int check_id, CheckStatus status) {
        try {
            DAOFactory.getCheckDAO().updateCheckStatus(check_id, status);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkDelete(int check_id) {
        try {
            DAOFactory.getCheckDAO().checkDelete(check_id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
