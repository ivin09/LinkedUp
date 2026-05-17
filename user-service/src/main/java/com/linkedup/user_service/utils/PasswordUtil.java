package com.linkedup.user_service.utils;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    //Hash the password
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    //Check a plain text password matches the previously hashed one
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
