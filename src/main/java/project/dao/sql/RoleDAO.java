package project.dao.sql;

import project.connection.ConnectionPool;
import project.dao.interfaces.IRoleDAO;
import project.entities.Role;
import project.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoleDAO implements IRoleDAO {
    private final int ROLEID = 1;
    private final int NAME = 2;
    @Override
    public Role getRole(int id) throws SQLException {
        final String SQL = "SELECT * FROM role WHERE id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setInt(1, id);
            return GetRoleFromResultSet(ps.executeQuery());
        }
    }

    @Override
    public Role getRole(String name) throws SQLException {
        final String SQL = "SELECT * FROM role WHERE name = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setString(1, name);
            return GetRoleFromResultSet(ps.executeQuery());
        }
    }

    @Override
    public List<Role> getAllRoles() throws SQLException {
        final String SQL = "SELECT * FROM role";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ResultSet result = ps.executeQuery();
            List<Role> roles = new ArrayList<Role>();
            while(result.next()) {
                roles.add(GetRoleFromResultSet(result));
            }
            return roles;
        }
    }

    @Override
    public Role getUserRole(User user) throws SQLException {
        final int id = user.getId();
        return getRole(id);
    }

    private Role GetRoleFromResultSet (ResultSet rs) {
        Role role = new Role();
        try {
            if (rs.isBeforeFirst()) rs.next();
            role.setId(rs.getInt(ROLEID));
            role.setName(rs.getString(NAME));
        } catch (SQLException e) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, e.getMessage());
            return null;
        }
        return role;
    }
}
