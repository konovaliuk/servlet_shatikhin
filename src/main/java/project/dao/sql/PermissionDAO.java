package project.dao.sql;

import project.connection.ConnectionPool;
import project.dao.interfaces.IPermissionDAO;
import project.entities.Permission;
import project.entities.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PermissionDAO implements IPermissionDAO {
    private final int PERMID = 1;
    private final int NAME = 2;

    @Override
    public Permission getPermission(int id) throws SQLException {
        final String SQL = "SELECT * FROM permission WHERE id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setInt(1, id);
            return GetPermissionFromResultSet(ps.executeQuery());
        }
    }

    @Override
    public List<Permission> getRolePermissions(Role role) throws SQLException {
        final int roleid = role.getId();
        final String SQL = "SELECT * FROM role_permission WHERE role_id = ?";
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setInt(1, roleid);
            ResultSet result = ps.executeQuery();
            List<Permission> perms = new ArrayList<Permission>();
            while (result.next()) {
                perms.add(getPermission(result.getInt(2)));
            }
            return perms;
        }
    }
    private Permission GetPermissionFromResultSet (ResultSet rs) {
        Permission permission = new Permission();
        try {
            if (rs.isBeforeFirst()) rs.next();
            permission.setId(rs.getInt(PERMID));
            permission.setName(rs.getString(NAME));
        } catch (SQLException e) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, e.getMessage());
            return null;
        }
        return permission;
    }
}
