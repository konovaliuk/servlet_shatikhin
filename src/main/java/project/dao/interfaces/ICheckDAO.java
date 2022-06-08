package project.dao.interfaces;
import project.entities.Check;
import project.entities.CheckProduct;
import project.entities.User;
import project.entities.enums.CheckStatus;

import java.sql.SQLException;
import java.util.List;

public interface ICheckDAO {
    Check getCheck(int id) throws SQLException;
    CheckProduct getCheckProduct(int check_id, int product_id) throws SQLException;
    void createCheck(Check check) throws SQLException;
    void checkAddProduct(int check_id, int product_id, float quantity) throws SQLException;
    void checkSetProduct(int check_id, int product_id, float quantity) throws  SQLException;
    void checkRemoveProduct(int check_id, int product_id) throws SQLException;
    double checkGetTotal(int id) throws SQLException;
    void checkUpdateTotal(int id) throws SQLException;
    void updateCheckStatus(int id, CheckStatus status) throws SQLException;
    List<CheckProduct> checkGetProducts(int id) throws SQLException;
    List<Check> getCashierChecks(User user) throws SQLException;
    List<Check> getAllChecks() throws SQLException;
    List<Check> getChecks(CheckStatus status) throws SQLException;
    void checkDelete(int id) throws SQLException;
}
