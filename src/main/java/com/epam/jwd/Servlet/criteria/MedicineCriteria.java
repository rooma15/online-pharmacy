package com.epam.jwd.Servlet.criteria;

import java.util.List;

public class MedicineCriteria extends AbstractCriteria{
    private final List<String> categories;
    private final List<String> consistencies;
    private Boolean isRecipe;

    public Boolean isRecipe() {
        return isRecipe;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<String> getConsistencies() {
        return consistencies;
    }

    public MedicineCriteria(int id, String name, List<String> categories, List<String> consistencies,
                            Boolean isRecipe) {
        super(id, name);
        this.categories = categories;
        this.consistencies = consistencies;
        this.isRecipe = isRecipe;
    }

    public MedicineCriteria(List<String> categories, List<String> consistencies, Boolean isRecipe) {
        this.categories = categories;
        this.consistencies = consistencies;
        this.isRecipe = isRecipe;
    }

    public static class MedicineCriteriaBuilder extends AbstractCriteriaBuilder<MedicineCriteriaBuilder,
            MedicineCriteria>{

        Integer id;
        String name;
        private List<String> categories;
        private List<String> consistencies;
        private Boolean isRecipe;

        public MedicineCriteriaBuilder categories(List<String> categories){
            this.categories = categories;
            return this;
        }

        public MedicineCriteriaBuilder consistencies(List<String> consistencies){
            this.consistencies = consistencies;
            return this;
        }

        public MedicineCriteriaBuilder recipe(Boolean isRecipe){
            this.isRecipe = isRecipe;
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
                return new MedicineCriteria(id, name, categories, consistencies, isRecipe);
            }else {
                return new MedicineCriteria(categories, consistencies, isRecipe);
            }
        }
    }
}
