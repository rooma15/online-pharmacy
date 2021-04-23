package com.epam.jwd.Servlet.Util;

import com.epam.jwd.ConnectionPool.DBConnector;
import com.epam.jwd.ConnectionPool.Impl.DBConnectionPool;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

public class UtilBankService {

    private Connection dbConnection;
    private Savepoint savepoint;

    public UtilBankService(){
        try {
            dbConnection = DBConnectionPool.getInstance().getConnection();
        }catch (InterruptedException e){
            Util.lOGGER.error(e.getMessage());
        }
    }

    public boolean makeTransaction(double price) {
        String st = "update bank.accounts set balance=balance+? where card_number='1234567891234566'";
        try {
            dbConnection.setAutoCommit(false);
            try (PreparedStatement statement = dbConnection.prepareStatement(st)) {
                statement.setDouble(1, price);
                savepoint = dbConnection.setSavepoint("savepoint");
                int rows = statement.executeUpdate();
                dbConnection.close();
                return rows != 0;
            } catch (SQLException e) {
                Util.lOGGER.error(e.getStackTrace());
            }
        } catch (SQLException e) {
            Util.lOGGER.error(e.getStackTrace());
        }
        return false;
    }

    public void commit() throws SQLException {
        dbConnection.commit();
        dbConnection.close();
    }

    public void rollBack() throws SQLException {
        if(savepoint != null){
            dbConnection.rollback(savepoint);
            dbConnection.close();
        }
    }

}
