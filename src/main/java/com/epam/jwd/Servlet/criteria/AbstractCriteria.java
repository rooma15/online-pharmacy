package com.epam.jwd.Servlet.criteria;

public class AbstractCriteria {
    protected final Integer id;
    protected final String name;

    public AbstractCriteria(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public AbstractCriteria() {
        id = 1;
        name = "default";
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static abstract class AbstractCriteriaBuilder<T extends AbstractCriteriaBuilder<T, E>, E extends AbstractCriteria>{
        public abstract T id(Integer id);
        public abstract T name(String name);
        public abstract E build();
    }
}
