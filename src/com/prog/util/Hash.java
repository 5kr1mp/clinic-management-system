package com.prog.util;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hash {
    
    /**
     * Hashes the password to securely store the password
     * to the database using PBKDF2WithHmacSHA256 algorithm
     * 
     * The bit length of the hash is 256 bits, and encoded
     * to String using Base64 encoder.
     * 
     * @param password the password to hash
     * @param salt the salt to be added to the password
     * @return the hashed password
     * @throws Exception
     */
    public static String hashPassword(String password, byte[] salt) throws Exception{
        int iterations = 10000;
        int keylength = 256;

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt,iterations, keylength);
        
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hash);
    }   


    /**
     * Generates a 128-bit salt to be added to
     * passwords to ensure no duplicate hashes
     * are generated even if passwords are the same.
     * 
     * @return the 128-bit salt
     */
    public static byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
