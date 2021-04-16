package com.epam.jwd.Servlet.model;

public enum UserRole {

    CLIENT(1),
    PHARMACIST(2),
    DOCTOR(3);

    private int id;

    UserRole(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public static UserRole of(int id){
        for(UserRole role : values()){
            if(role.getId() == id){
                return role;
            }
        }
        return CLIENT;
    }

}
