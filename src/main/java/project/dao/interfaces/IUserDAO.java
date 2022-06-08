package project.dao.interfaces;

import project.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    User getUser(int id) throws SQLException;
    User getUser(String username) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    void createUser(User user) throws SQLException;
}
