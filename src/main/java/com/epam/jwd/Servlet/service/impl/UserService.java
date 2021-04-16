package com.epam.jwd.Servlet.service.impl;

import com.epam.jwd.Servlet.dao.impl.UserDAO;
import com.epam.jwd.Servlet.model.User;
import com.epam.jwd.Servlet.model.UserDto;
import com.epam.jwd.Servlet.model.UserRole;
import com.epam.jwd.Servlet.service.CommonService;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * service for handling with users
 */
public class UserService implements CommonService<UserDto> {

    private final UserDAO userDao;


    /**
     * initializes user DAO {@link UserDAO}
     */
    public UserService() {
        userDao = new UserDAO();
    }


    /**
     * fins all users in database
     * @return {@link List} of {@link UserDto} users
     */
    @Override
    public List<UserDto> findAll() {
        List<User> users = new ArrayList<>();
        userDao.findAll().forEach((user -> user.ifPresent(users::add)));
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * delete user from database by id
     * @param id of user to be deleted
     * @return true if user was deleted, false otherwise
     */
    @Override
    public boolean deleteById(int id) {
        Optional<UserDto> user = findById(id);
        if(user.isPresent()){
            if(user.get().getRole().equals(UserRole.PHARMACIST.name().toLowerCase())){
                return false;
            }
        }
        return userDao.deleteById(id);
    }


    /**
     * finds user with concrete id
     * @param id id of user
     * @return {@link Optional} of user if he exists, empty {@link Optional} otherwise
     */
    @Override
    public Optional<UserDto> findById(int id) {
        return userDao.findById(id).map(this::convertToDto);
    }


    /**
     * add user in database
     * @param user {@link UserDto}
     * @return true if user was created, false otherwise
     */
    public boolean create(UserDto user) {
        return userDao.create(new User(user.getLogin(),
                user.getPassword(),
                user.getName(),
                UserRole.valueOf(user.getRole().toUpperCase())));
    }


    /**
     * check if given pair of login and password is valid
     * @param login user login
     * @param password user password
     * @return {@link Optional} of {@link UserDto} if it exists, empty {@link Optional} otherwise
     */
    public Optional<UserDto> login(String login, String password){
        Optional<User> user = userDao.findByLogin(login);
        if(user.isPresent()){
            if(BCrypt.checkpw(password, user.get().getPassword())){
                return user.map(this::convertToDto);
            }else{
                return Optional.empty();
            }
        }else{
            return Optional.empty();
        }
    }


    /**
     * find user by given login
     * @param login user login
     * @return {@link Optional} of {@link User} of founded user, empty Optional otherwise
     */
    public Optional<User> findByLogin(String login){
        return userDao.findByLogin(login);
    }


    /**
     * converts {@link User} to {@link UserDto}
     * @param user given {@link User}
     * @return converted {@link UserDto}
     */
    private UserDto convertToDto(User user){
        return new UserDto(user.getLogin(), user.getPassword(), user.getName(), user.getRole());
    }
}
