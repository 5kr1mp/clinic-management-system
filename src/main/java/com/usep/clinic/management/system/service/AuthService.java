package com.usep.clinic.management.system.service;

import com.usep.clinic.management.system.dao.UserDao;
import com.usep.clinic.management.system.model.*;
import com.usep.clinic.management.system.model.enums.*;
import com.usep.clinic.management.system.util.Hash;

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
        
        if(password.length() < 8) throw new InvalidPasswordLengthException();
        
        byte[] salt = Hash.generateSalt();
        String hashedPassword = Hash.hashPassword(password, salt);

        User user = new User(username, hashedPassword, role);

        dao.addUser(user,salt);
    }
    
    public boolean patientLogin() throws Exception{
        String username = "USeP-Clinic-Patient";
        String pw = "USeP-Clinic-Patient-Role";
        if (!attemptLogin(username, pw)){
            registerUser(username, pw, Role.PATIENT);
        }
        
        return true;
    }
    
    /**
     * Attempts login and returns a {@code boolean} value to indicate if the
     * operation is successful. If user is successfully logged in, sets {@link
     * #currentUser} to the retrieved user.
     * 
     * @param username the username
     * @param password the un-hashed password
     * @return true if login is successful
     * @throws Exception thrown when hash algorithm is not found.
     */
    public boolean attemptLogin(String username, String password) throws Exception{

        User user = dao.getUser(username); 

        // if user does not exist
        if(user == null) return false; 

        // hashes the password input
        byte[] salt = dao.getSalt(username);
        String hashedInputPassword = Hash.hashPassword(password, salt);

        // if input password does not match the user's password
        if (!hashedInputPassword.equals(user.getPassword())) return false;
        
        // set user to currentUser
        currentUser = user;

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
