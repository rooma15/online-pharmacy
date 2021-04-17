package com.epam.jwd.Servlet.dao.impl;

import com.epam.jwd.ConnectionPool.Impl.DBConnectionPool;
import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.model.OrderItem;
import com.epam.jwd.Servlet.model.Prescription;
import com.epam.jwd.Util;

import javax.persistence.criteria.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CartDAO extends AbstractDAO<OrderItem> {

    private String DELETE_BY_ID = "delete from pharmacy.Cart where id=?";
    private String CREATE_ORDER_ITEM = "insert into pharmacy.Cart values(null, ?, ?, ?, ?)";

    @Override
    public List<Optional<OrderItem>> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<OrderItem> findById(int id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        return super.deleteById(id, DELETE_BY_ID);
    }

    @Override
    public boolean create(OrderItem item) {
        try (Connection dbConnection = connectionPool.getConnection()) {
            try (PreparedStatement statement = dbConnection.prepareStatement(CREATE_ORDER_ITEM)) {
                statement.setInt(1, item.getMedicineId());
                statement.setInt(2, item.getAmount());
                statement.setDouble(3, item.getPrice());
                statement.setInt(4, item.getUserId());
                int rows = statement.executeUpdate();
                if(rows > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                Util.lOGGER.error(e.getStackTrace());
            }
        } catch (InterruptedException | SQLException e) {
            Util.lOGGER.error(e.getStackTrace());
        }
        return false;
    }

    public List<OrderItem> findByCriteria(String st, String paramString, Object... params) {
        List<OrderItem> items = new ArrayList<>();
        try (Connection dbConnection = DBConnectionPool.getInstance().getConnection()) {
            try (PreparedStatement statement = dbConnection.prepareStatement(st)) {
                for(int i = 0; i < paramString.length(); i++) {
                    char type = paramString.charAt(i);
                    switch (type) {
                        case 'i':
                            statement.setInt(i + 1, (int) params[i]);
                            break;
                        case 's':
                            statement.setString(i + 1, (String) params[i]);
                            break;
                        case 'b':
                            statement.setBoolean(i + 1, (boolean) params[i]);
                            break;
                        case 'd':
                            statement.setDouble(i + 1, (double) params[i]);
                            break;
                    }
                }
                try (ResultSet result = statement.executeQuery()) {
                    while(result.next()) {
                        OrderItem item = new OrderItem(
                                result.getInt(1),
                                result.getInt(2),
                                result.getInt(5),
                                result.getInt(3),
                                result.getDouble(4)
                        );
                        items.add(item);
                    }
                } catch (SQLException e) {
                    Util.lOGGER.error(e.getStackTrace());
                }
            } catch (SQLException e) {
                Util.lOGGER.error(e.getStackTrace());
            }
        } catch (InterruptedException | SQLException e) {
            Util.lOGGER.error(e.getStackTrace());
        }
        return items;
    }

}
