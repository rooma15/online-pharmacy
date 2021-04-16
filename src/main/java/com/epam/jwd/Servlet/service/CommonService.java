package com.epam.jwd.Servlet.service;

import com.epam.jwd.Servlet.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface CommonService<T> {
    List<T> findAll();
    boolean deleteById(int id);
    Optional<T> findById(int id);
}
