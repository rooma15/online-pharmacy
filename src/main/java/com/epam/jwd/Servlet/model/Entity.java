package com.epam.jwd.Servlet.model;

public abstract class Entity {

    protected int id;

    protected int getId(){
        return id;
    }

    public Entity(int id){
        this.id = id;
    }

    public Entity(){
        this.id = 0;
    }

}
