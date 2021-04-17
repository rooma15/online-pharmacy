package com.epam.jwd.Servlet.service;

import com.epam.jwd.Servlet.criteria.AbstractCriteria;

import java.util.List;


public interface FilterService<E extends AbstractCriteria, T> {
    List<T> filter(E criteria, List<T> items);
}
