package project.dao.sql;

import project.connection.ConnectionPool;
import project.dao.interfaces.ICheckDAO;
import project.entities.Check;
import project.entities.CheckProduct;
import project.entities.Product;
import project.entities.User;
import project.entities.enums.CheckStatus;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckDAO implements ICheckDAO {
    @Override
    public Check getCheck(int id) throws SQLException {
        final String SQL = "SELECT * FROM `check` WHERE id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setInt(1, id);
            return GetCheckFromResultSet(ps.executeQuery());
        }
    }

    @Override
    public CheckProduct getCheckProduct(int check_id, int product_id) throws SQLException {
        String SQL = "SELECT * FROM check_product WHERE check_id = ? AND product_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setInt(1, check_id);
            ps.setInt(2, product_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CheckProduct(check_id, product_id, rs.getFloat(3));
            }
            else return null;
        }
    }

    @Override
    public void createCheck(Check check) throws SQLException {
        final String SQL = "INSERT INTO `check` (timestamp, status, cost, cashier_id) VALUES (?, ?, ?, ?);";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            if (check.getTimestamp() == null) ps.setTimestamp(1, Timestamp.from(Instant.now()));
            else ps.setTimestamp(1, check.getTimestamp());
            ps.setString(2, check.getStatus().name());
            ps.setDouble(3, check.getCost());
            ps.setInt(4, check.getCashier_id());
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    check.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating check failed");
                }
            }
        }
    }

    @Override
    // quantity can be negative -- that means decreasing amount of product in check
    public void checkAddProduct(int check_id, int product_id, float quantity) throws SQLException {
        if (GetCheckStatus(check_id) != CheckStatus.OPENED) {
            throw new SQLException("Check " + check_id + " is closed");
        }
        float baseQuantity = 0;
        CheckProduct checkProduct = getCheckProduct(check_id, product_id);
        if (checkProduct != null) baseQuantity = checkProduct.getQuantity();
        if (baseQuantity == 0) {
            if (quantity < 0) throw new SQLException("Can't add negative amount of products to check");
            String SQL = "INSERT INTO check_product (check_id, product_id, quantity) VALUES (?, ?, ?)";
            try (Connection connection = ConnectionPool.getConnection();
                 PreparedStatement ps = connection.prepareStatement(SQL)
            ) {
                ps.setInt(1, check_id);
                ps.setInt(2, product_id);
                ps.setFloat(3, quantity);
                ps.executeUpdate();
            }
        }
        else {
            float amount = baseQuantity + quantity;
            if (amount < 0) amount = 0;
            String SQL = "UPDATE check_product SET quantity = ? WHERE check_id = ? AND product_id = ?";
            try (Connection connection = ConnectionPool.getConnection();
                 PreparedStatement ps = connection.prepareStatement(SQL)
            ) {
                ps.setFloat(1, amount);
                ps.setInt(2, check_id);
                ps.setInt(3, product_id);
                ps.executeUpdate();
            }
        }
        checkUpdateTotal(check_id);
    }

    @Override
    public void checkSetProduct(int check_id, int product_id, float quantity) throws SQLException {
        if (GetCheckStatus(check_id) != CheckStatus.OPENED) {
            throw new SQLException("Check " + check_id + " is closed");
        }
        CheckProduct checkProduct = getCheckProduct(check_id, product_id);
        if (checkProduct == null) throw new SQLException("This product is not added to check yet");
        checkProduct.setQuantity(quantity);
        String SQL = "UPDATE check_product SET quantity = ? WHERE check_id = ? AND product_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setFloat(1, quantity);
            ps.setInt(2, check_id);
            ps.setInt(3, product_id);
            ps.executeUpdate();
        }
        checkUpdateTotal(check_id);
    }

    @Override
    public void checkRemoveProduct(int check_id, int product_id) throws SQLException {
        if (GetCheckStatus(check_id) != CheckStatus.OPENED) {
            throw new SQLException("Check " + check_id + " is closed");
        }
        String SQL = "DELETE FROM check_product WHERE check_id = ? AND product_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setInt(1, check_id);
            ps.setInt(2, product_id);
            ps.executeUpdate();
        }
        checkUpdateTotal(check_id);
    }
    @Override
    public double checkGetTotal(int id) throws SQLException {
        List<CheckProduct> products = checkGetProducts(id);
        double total = 0;
        for (CheckProduct checkproduct : products) {
            ProductDAO dao = new ProductDAO();
            Product product = dao.getProduct(checkproduct.getProduct_id());
            total += product.getPrice() * checkproduct.getQuantity();
        }
        return total;
    }

    @Override
    public void checkUpdateTotal(int id) throws SQLException {
        final String SQL = "UPDATE `check` SET cost = ? WHERE id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            double total = checkGetTotal(id);
            ps.setDouble(1, total);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void updateCheckStatus(int id, CheckStatus status) throws SQLException {
        final String SQL = "UPDATE `check` SET status = ? WHERE id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setString(1, status.name());
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<CheckProduct> checkGetProducts(int id) throws SQLException {
        final String SQL = "SELECT product_id, quantity FROM check_product WHERE check_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            List<CheckProduct> products = new ArrayList<CheckProduct>();
            while(result.next()) {
                products.add(new CheckProduct(id, result.getInt(1), result.getFloat(2)));
            }
            return products;
        }
    }
    private CheckStatus GetCheckStatus(int check_id) throws SQLException {
        return getCheck(check_id).getStatus();
    }
    private Check GetCheckFromResultSet (ResultSet rs) {
        Check check = new Check();
        try {
            if (rs.isBeforeFirst()) rs.next();
            //rs.next();
            check.setId(rs.getInt(1));
            check.setTimestamp(rs.getTimestamp(2));
            check.setStatus(CheckStatus.valueOf(rs.getString(3)));
            check.setCost(rs.getDouble(4));
            check.setCashier_id(rs.getInt(5));

        } catch (SQLException e) {
            Logger.getLogger(CheckDAO.class.getName()).log(Level.SEVERE, e.getMessage());
            return null;
        }
        return check;
    }

    @Override
    public List<Check> getAllChecks() throws SQLException {
        final String SQL = "SELECT * from `check`";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ResultSet result = ps.executeQuery();
            List<Check> checks = new ArrayList<Check>();
            while(result.next()) {
                checks.add(GetCheckFromResultSet(result));
            }
            return checks;
        }
    }

    @Override
    public List<Check> getChecks(CheckStatus status) throws SQLException {
        final String SQL = "SELECT * from `check` WHERE status = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setString(1, status.name());
            ResultSet result = ps.executeQuery();
            List<Check> checks = new ArrayList<Check>();
            while(result.next()) {
                checks.add(GetCheckFromResultSet(result));
            }
            return checks;
        }
    }

    @Override
    public List<Check> getCashierChecks(User user) throws SQLException {
        final String SQL = "SELECT * from `check` WHERE cashier_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setInt(1, user.getId());
            ResultSet result = ps.executeQuery();
            List<Check> checks = new ArrayList<Check>();
            while(result.next()) {
                checks.add(GetCheckFromResultSet(result));
            }
            return checks;
        }
    }

    @Override
    public void checkDelete(int id) throws SQLException {
        final String Q1 = "DELETE from `check_product` WHERE check_id = ?";
        final String Q2 = "DELETE from `check` WHERE id = ?";
        Connection connection = ConnectionPool.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(Q1);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps = connection.prepareStatement(Q2);
            ps.setInt(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("checkDelete procedure rollback");
            throw e;
        } finally {
            connection.close();
        }
    }
}
