package by.rozmysl.booking.model.service.validator;

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
public final class PasswordEncryptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncryptor.class);
    private static final int ITERATIONS = 20 * 1000;
    private static final int SALT_LEN = 32;
    private static final int DESIRED_KEY_LEN = 256;

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private PasswordEncryptor() {
    }

    /**
     * Computes a salted PBKDF2 hash of given plaintext password
     *
     * @param password password
     * @return encoded password
     */
    public static String getSaltedHash(String password) {
        byte[] salt = new byte[0];
        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(SALT_LEN);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(String.valueOf(e));
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
        SecretKeyFactory keyFactory;
        SecretKey key = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            key = keyFactory.generateSecret(new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, DESIRED_KEY_LEN));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error(String.valueOf(e));
        }
        return Base64.encodeBase64String(key != null ? key.getEncoded() : new byte[0]);
    }
}
