package main.java.util;

//import at.favre.lib.crypto.bcrypt.BCrypt;
import org.mindrot.jbcrypt.*;
import java.lang.Exception;

public class PasswordUtil {
    public static String hashPasswd(String passwd) {
        String salt = BCrypt.gensalt(12);
        String hashed_pass = BCrypt.hashpw(passwd, salt);
        return hashed_pass;
    }

    public static boolean checkPasswd(String cand, String hashed) {
        boolean passwd_verified = false;
        if (null == hashed || !hashed.startsWith("$2a$")) 
            throw new IllegalArgumentException("Invalid argumen provided for comparison");

        passwd_verified = BCrypt.checkpw(cand, hashed);
        System.out.println(passwd_verified);
        return passwd_verified;
    }
}
