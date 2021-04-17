package com.epam.jwd.Servlet.criteria;

import java.util.List;

public class MedicineCriteria extends AbstractCriteria{
    private final List<String> categories;
    private final List<String> consistencies;

    public List<String> getCategories() {
        return categories;
    }

    public List<String> getConsistencies() {
        return consistencies;
    }

    public MedicineCriteria(int id, String name, List<String> categories, List<String> consistencies) {
        super(id, name);
        this.categories = categories;
        this.consistencies = consistencies;
    }

    public MedicineCriteria(List<String> categories, List<String> consistencies) {
        this.categories = categories;
        this.consistencies = consistencies;
    }

    public static class MedicineCriteriaBuilder extends AbstractCriteriaBuilder<MedicineCriteriaBuilder,
            MedicineCriteria>{

        Integer id;
        String name;
        private List<String> categories;
        private List<String> consistencies;

        public MedicineCriteriaBuilder categories(List<String> categories){
            this.categories = categories;
            return this;
        }

        public MedicineCriteriaBuilder consistencies(List<String> consistencies){
            this.consistencies = consistencies;
            return this;
        }

        @Override
        public MedicineCriteriaBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        @Override
        public MedicineCriteriaBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public MedicineCriteria build() {
            if(id != null && name != null){
                return new MedicineCriteria(id, name, categories, consistencies);
            }else {
                return new MedicineCriteria(categories, consistencies);
            }
        }
    }
}
