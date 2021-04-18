package com.epam.jwd.Servlet.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.apache.commons.text.StringEscapeUtils;

public class User extends Entity{
    private final String login;
    private final String password;
    private final String name;

    private final UserRole role;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return StringEscapeUtils.unescapeHtml4(login);
    }

    public String getPassword() {
        return password;
    }

    public User(int id, String login, String password, String name, UserRole role) {
        super(id);
        this.login = login;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public User(String login, String password, String name, UserRole role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public User(ResultSet resultSet) throws SQLException {
        super(resultSet.getInt(1));
        this.login = resultSet.getString(2);
        this.password = resultSet.getString(3);
        this.name = resultSet.getString(4);
        this.role = UserRole.of(resultSet.getInt(5));
    }

    public UserRole getRole(){
        return role;
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, name, role);
    }
}
