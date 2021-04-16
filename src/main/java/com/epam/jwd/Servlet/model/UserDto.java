package com.epam.jwd.Servlet.model;

import java.util.Objects;

public class UserDto {
    private String login;
    private String password;
    private String name;
    private UserRole role;
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserDto(String login, String name) {
        this.login = login;
        this.name = name;
        this.password = null;
    }

    public UserDto(String login, String password, String name, UserRole role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(login, userDto.login) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(name, userDto.name) &&
                role == userDto.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, name, role);
    }

    public String getRole(){
        return role.name().toLowerCase();
    }
}
