package com.prog.model;

import com.prog.model.enums.Role;

public class User {

    private Role role;
    private String userName;
    private String password;

    public User(String userName, String hashedPassword, Role role){
        this.userName = userName;
        this.password = hashedPassword;
        this.role = role;
    }

    public String getUserName() {return userName;}
    public String getPassword(){return password;}
    public Role getRole() {return role;}
    
}
