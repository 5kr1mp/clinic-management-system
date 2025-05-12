package com.usep.clinic.management.system.model;

import com.usep.clinic.management.system.model.enums.Role;

public class User {

    private Role role;
    private String username;
    private String password;

    public User(String userName, String hashedPassword, Role role){
        this.username = userName;
        this.password = hashedPassword;
        this.role = role;
    }

    public String getUsername() {return username;}
    public String getPassword(){return password;}
    public Role getRole() {return role;}
    
}
