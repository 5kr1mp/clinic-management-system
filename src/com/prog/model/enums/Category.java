package com.prog.model.enums;

public enum Category {
    
    STUDENT("Student"),
    FACULTY("Faculty");

    private final String name;

    Category(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
