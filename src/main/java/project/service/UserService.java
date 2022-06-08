package project.service;

import org.mindrot.jbcrypt.BCrypt;
import project.dao.DAOFactory;
import project.entities.Check;
import project.entities.User;
import project.entities.enums.CheckStatus;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class UserService {
    public boolean checkCredentials(String username, String password) {
        User user = find(username);
        if (user == null) return false;
        return BCrypt.checkpw(password, user.getPassword());
    }
    public User find(int id) {
        User user;
        try {
            user = DAOFactory.getUserDAO().getUser(id);
        } catch (SQLException e) {
            return null;
        }
        return user;
    }
    public User find(String username) {
        User user;
        try {
            user = DAOFactory.getUserDAO().getUser(username);
        } catch (SQLException e) {
            return null;
        }
        return user;
    }
    public List<User> getAllUsers() {
        try {
            List<User> users = DAOFactory.getUserDAO().getAllUsers();
            return users;
        } catch (SQLException e) {
            return null;
        }
    }
    public int createUser(User user) {
        try {
            DAOFactory.getUserDAO().createUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return user.getId();
    }
}
