package project.dao.interfaces;

import project.entities.Role;
import project.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface IRoleDAO {
    Role getRole(int id) throws SQLException;
    Role getRole(String name) throws SQLException;
    List<Role> getAllRoles() throws SQLException;
    Role getUserRole(User user) throws SQLException;
}
