package com.epam.jwd.test;

import com.epam.jwd.ConnectionPool.ConnectionPool;
import com.epam.jwd.ConnectionPool.Impl.DBConnectionPool;
import com.epam.jwd.Servlet.model.User;
import com.epam.jwd.Servlet.model.UserDto;
import com.epam.jwd.Servlet.model.UserRole;
import com.epam.jwd.Servlet.service.impl.UserService;
import com.epam.jwd.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;

public class UserServiceTest {

    ConnectionPool connectionPool = DBConnectionPool.getInstance();
    UserService service = new UserService();

    @Before
    public  void createConnections(){
        try {
            connectionPool.init();
        } catch (SQLException e) {
            Util.lOGGER.error(e.getMessage());
        }
    }

    @After
    public void destroyConnections(){
        connectionPool.destroy();
    }

    @Test
    public void findAllTest(){
        UserDto dto1 = new UserDto("nastya-lox", "$2a$10$LIaPwJ6yD0YBtCDIzgSguutk1jmQJvKvmizgeOQmK9L67ksa5neWu", "nastya", UserRole.PHARMACIST);
        UserDto dto2 = new UserDto("aleshka", "$2a$10$Z0.tLyIibMYRU.QiHKtBsONMRnm4JwU8d1unfSLZD7hi8GRK/Syhi", "lexa", UserRole.DOCTOR);
        assertEquals(dto1, service.findAll().get(0));
        assertEquals(dto2, service.findAll().get(1));
    }


    @Test
    public void deleteByIdTest(){
        assertEquals(true, service.deleteById(63));
    }

    @Test
    public void findByIdTest(){
        UserDto dto1 = new UserDto("nastya-lox", "$2a$10$LIaPwJ6yD0YBtCDIzgSguutk1jmQJvKvmizgeOQmK9L67ksa5neWu", "nastya", UserRole.PHARMACIST);
        assertEquals(dto1, service.findById(31).get());
    }

    @Test
    public void findByLoginTest(){
        User user = new User(31, "nastya-lox", "$2a$10$LIaPwJ6yD0YBtCDIzgSguutk1jmQJvKvmizgeOQmK9L67ksa5neWu", "nastya", UserRole.PHARMACIST);
        assertEquals(user, service.findByLogin("nastya-lox").get());
    }

    @Test
    public void loginTest(){
        UserDto dto1 = new UserDto("nastya-lox", "$2a$10$LIaPwJ6yD0YBtCDIzgSguutk1jmQJvKvmizgeOQmK9L67ksa5neWu", "nastya", UserRole.PHARMACIST);
        assertEquals(dto1, service.login("nastya-lox", "alex2002").get());
    }
}
