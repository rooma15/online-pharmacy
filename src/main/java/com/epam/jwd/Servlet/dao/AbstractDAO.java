package com.epam.jwd.Servlet.dao;

import com.epam.jwd.ConnectionPool.ConnectionPool;
import com.epam.jwd.ConnectionPool.Impl.DBConnectionPool;
import com.epam.jwd.Servlet.model.Entity;
import com.epam.jwd.Servlet.Util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractDAO<T extends Entity> {

    protected final ConnectionPool connectionPool = DBConnectionPool.getInstance();

    public abstract List<Optional<T>> findAll();


    /**
     * find all entites in database
     *
     * @param st          statement to be executed
     * @param entityAdder functional interface, used to create entity
     * @return {@link List} of {@link Optional} of entities
     */
    protected List<Optional<T>> findAll(String st, Function<ResultSet, Optional<T>> entityAdder) {
        return AbstractDAO.<T>findByCriteria(st, "", entityAdder);
    }

    public abstract Optional<T> findById(int id);


    /**
     * find  entity by id in database
     *
     * @param id       if of entity
     * @param st       statement to be executed
     * @param function functional interface, used to create entity
     * @return {@link Optional} of {@link Entity}
     */
    protected Optional<T> findById(int id, String st, Function<ResultSet, Optional<T>> function) {
        List<Optional<T>> items = AbstractDAO.<T>findByCriteria(st, "i", function, id);
        if(items.isEmpty()) {
            return Optional.empty();
        } else {
            return items.get(0);
        }
    }

    public abstract boolean deleteById(int id);

    protected boolean deleteById(int id, String st) {
        return updateByCriteria(st, "i", id);
    }

    ;

    public abstract boolean create(T entity);


    /**
     * uses to get data from small tables with one field
     *
     * @param query query to be executed
     * @return {@link List} of some values
     */
    public static List<String> getOptions(String query) {
        List<String> elems = new ArrayList<>();
        try (Connection dbConnection = DBConnectionPool.getInstance().getConnection()) {
            try (Statement statement = dbConnection.createStatement()) {
                try (ResultSet result = statement.executeQuery(query)) {
                    while(result.next()) {
                        elems.add(result.getString(1));
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
        return elems;
    }

    /**
     * used to perform all update operations with database
     *
     * @param st          statemnt to be executed
     * @param paramString string of parameter types
     *                    i: int, s: {@link String}, d: double, b: boolean
     * @param params      list of params used in statement
     * @return true if update was successful, false otherwise
     */
    public static boolean updateByCriteria(String st, String paramString, Object... params) {
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
                        case 't':
                            statement.setTimestamp(i + 1, (Timestamp) params[i]);
                            break;
                    }
                }
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


    /**
     * finds something in database using sql expression
     *
     * @param st          sql expression to select something from database
     * @param paramString string of parameter types
     *                    i: int, s: {@link String}, d: double, b: boolean
     * @param entityAdder {@link Function} with this interface you must delegate
     *                    creation of Entities
     *                    with selected information from database
     * @param params      parameters of selection that will be inserted into sql query
     * @return {@link List} of {@link Optional} of template value
     */
    public static <E extends Entity> List<Optional<E>> findByCriteria(String st, String paramString, Function<ResultSet,
            Optional<E>> entityAdder,
                                                                      Object... params) {
        List<Optional<E>> items = new ArrayList<>();
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
                        case 't':
                            statement.setTimestamp(i + 1, (Timestamp) params[i]);
                            break;
                    }
                }
                try (ResultSet result = statement.executeQuery()) {
                    while(result.next()) {
                        items.add(entityAdder.apply(result));
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
