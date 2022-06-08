package project.connection;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConnectionPool {
    private static final BasicDataSource dataSource = new BasicDataSource();
    static {
        ResourceBundle resource = ResourceBundle.getBundle("db");
        dataSource.setDriverClassName(resource.getString("driver"));
        dataSource.setUrl(resource.getString("url"));
        dataSource.setUsername(resource.getString("username"));
        dataSource.setPassword(resource.getString("password"));
    }
    private ConnectionPool() {}
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
