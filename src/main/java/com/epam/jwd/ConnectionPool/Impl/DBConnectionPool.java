package com.epam.jwd.ConnectionPool.Impl;

import com.epam.jwd.ConnectionPool.ConnectionPool;
import com.epam.jwd.ConnectionPool.DBConnector;
import com.epam.jwd.ConnectionPool.ProxyConnection;
import com.epam.jwd.Servlet.Util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DBConnectionPool implements ConnectionPool {


    private final Stack<ProxyConnection> connections;
    private final int DEFAULT_CAPACITY = 10;
    private int amountOfConnections;
    private static DBConnectionPool connectionPool;

    Lock lock;
    Condition notEmpty;


    private DBConnectionPool() {
        connections = new Stack<>();
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        amountOfConnections = DEFAULT_CAPACITY;
    }

    public void init() throws SQLException {
        for(int i = 0; i < DEFAULT_CAPACITY; i++){
            Util.lOGGER.info("creating " + (i + 1) + " connection");
            ProxyConnection connection = new ProxyConnection(DBConnector.getConnection());
            this.connections.push(connection);
        }

    }

    public static DBConnectionPool getInstance() {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            if(connectionPool == null) {
                connectionPool = new DBConnectionPool();
            }
        }finally {
            lock.unlock();
        }

        return connectionPool;
    }

    @Override
    public Connection getConnection() throws InterruptedException {
        Util.lOGGER.info("took a connection");
        lock.lock();
        Connection con;
        try {
            while(this.amountOfConnections == 0) {
                this.notEmpty.await();
            }
            con = connections.pop();
            --amountOfConnections;
        } finally {
            lock.unlock();
        }
        return con;
    }

    public int getAmountOfConnections() {
        return amountOfConnections;
    }

    @Override
    public void putConnection(Connection connection) throws InterruptedException {
        this.lock.lock();

        try {
            connections.push((ProxyConnection) connection);
            ++amountOfConnections;
            notEmpty.signal();
        }catch (Exception e){
              Util.lOGGER.error(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public void destroy(){
        Util.lOGGER.info("start destroying all connections");
        try {
            connections.forEach(ProxyConnection::destroyConnection);
        } catch (Exception e) {
            Util.lOGGER.error(e.getMessage());
        }
    }

}
