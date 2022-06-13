package project.service;

import org.mindrot.jbcrypt.BCrypt;
import project.dao.DAOFactory;
import project.entities.User;

import java.sql.SQLException;
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
            e.printStackTrace();
            return null;
        }
        return user;
    }

    public User find(String username) {
        try {
            return DAOFactory.getUserDAO().getUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getAllUsers() {
        try {
            return DAOFactory.getUserDAO().getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
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
