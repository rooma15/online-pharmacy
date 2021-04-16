package com.epam.jwd.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    Connection getConnection() throws InterruptedException;
    void putConnection(Connection connection) throws InterruptedException;
    void init() throws SQLException;
    void destroy();

}
