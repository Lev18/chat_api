package main.java.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {
    public static String hashPasswd(String passwd) {
        return BCrypt.withDefaults().hashToString(12, passwd.toCharArray());
    }

    public static boolean checkPasswd(String cand, String hashed) {
        return BCrypt.verifyer().verify(cand.toCharArray(), hashed).verified;
    }
}
