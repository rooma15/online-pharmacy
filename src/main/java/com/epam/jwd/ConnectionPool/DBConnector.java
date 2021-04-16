package com.epam.jwd.ConnectionPool;

import com.epam.jwd.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public abstract class DBConnector {
    public static Connection getConnection() throws SQLException {
        ResourceBundle bundle = ResourceBundle.getBundle("database");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Util.lOGGER.error("something occured with driver");
        }
        String url = bundle.getString("db.url");
        String user = bundle.getString("db.user");
        String pass = bundle.getString("db.password");
        Connection connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }
}
