package utils;


import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordUtil {

    public static String hashPassword(String plainPassword) {
        Argon2 argon2 = Argon2Factory.create();

        try {
            int iterations = 2;
            int memory = 65536; 
            int parallelism = 1;

            return argon2.hash(iterations, memory, parallelism, plainPassword.toCharArray());
        } finally {
            argon2.wipeArray(plainPassword.toCharArray()); 
        }
    }

    public static boolean verifyPassword(String hashedPassword, String plainPassword) {
        Argon2 argon2 = Argon2Factory.create();

        return argon2.verify(hashedPassword, plainPassword.toCharArray());
    }
}
