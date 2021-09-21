package by.rozmysl.bookingServlet.security;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a password encoding service.
 */
public class PasswordAuthentication {
    private static final Logger logger = LoggerFactory.getLogger(PasswordAuthentication.class);
    private static final int iterations = 20 * 1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;

    /**
     * Computes a salted PBKDF2 hash of given plaintext password
     *
     * @param password password
     * @return encoded password
     */
    public static String getSaltedHash(String password) {
        byte[] salt = new byte[0];
        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        } catch (NoSuchAlgorithmException e) {
            logger.error(String.valueOf(e));
        }
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    /**
     * Checks whether given plaintext password corresponds to a stored salted hash of the password
     *
     * @param password password
     * @param stored   stored
     * @return verification result
     */
    public static boolean check(String password, String stored) {
        String[] saltAndPass = stored.split("\\$");
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    /**
     * Hashes the password
     *
     * @param password password
     * @param salt     salt
     * @return hash
     */
    private static String hash(String password, byte[] salt) {
        SecretKeyFactory f;
        SecretKey key = null;
        try {
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            key = f.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, desiredKeyLen));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error(String.valueOf(e));
        }
        return Base64.encodeBase64String(key != null ? key.getEncoded() : new byte[0]);
    }
}
