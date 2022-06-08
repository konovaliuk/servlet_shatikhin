package project.dao;

import project.dao.interfaces.*;
import project.dao.sql.*;

public class DAOFactory {
    public static IUserDAO getUserDAO() {
        return new UserDAO();
    }
    public static IRoleDAO getRoleDAO() { return new RoleDAO(); }
    public static IPermissionDAO getPermissionDAO() { return new PermissionDAO(); }
    public static IProductDAO getProductDAO() { return new ProductDAO(); }
    public static ICheckDAO getCheckDAO() { return new CheckDAO(); }
}
