package com.prog.service;

import com.prog.dao.UserDao;
import com.prog.model.*;
import com.prog.model.enums.*;
import com.prog.util.Hash;

public class AuthService {
    
    private static AuthService instance;
    
    private User currentUser;
    private UserDao dao;

    public static AuthService getInstance(){
        if (instance == null){
            instance = new AuthService(new UserDao());
        }

        return instance;
    } 

    private AuthService(UserDao dao){
        this.dao = dao;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }

    public Role getLoggedInUserRole(){
        return currentUser.getRole();
    }

    public void registerUser(String username, String password, Role role) throws Exception, DuplicateEntityException{

        if(dao.getUser(username) != null) throw new DuplicateEntityException("Username already exists.");
        
        byte[] salt = Hash.generateSalt();
        String hashedPassword = Hash.hashPassword(password, salt);

        User user = new User(username, hashedPassword, role);

        dao.addUser(user,salt);
    }

    public boolean attemptLogin(String username, String password) throws Exception{

        User user = dao.getUser(username); 

        // if user does not exist
        if(user == null) return false; 

        // hashes the password input
        byte[] salt = dao.getSalt(username);
        String hashedInputPassword = Hash.hashPassword(password, salt);

        // if input password does not match the user's password
        if (!hashedInputPassword.equals(user.getPassword())) return false;

        return true;
    }

    public boolean updatePassword(String username, String password, String newPassword) throws Exception{
        
        // generate new salt and hash password
        byte[] newSalt = Hash.generateSalt();
        String newHashedPassword = Hash.hashPassword(newPassword, newSalt);

        // updates password when login attempt is succesful
        if (attemptLogin(username, password)) {
            dao.setPassword(username,newHashedPassword,newSalt);
            return true;
        }
        
        return false;
    }

    public boolean deleteUser(String username, String password) throws Exception{

        if (attemptLogin(username, password)){
            dao.deleteUser(username);
            return true;
        }

        return false;
    }

}
