package logic.model;
import logic.exception.PwdHasherException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 *  The PasswordHasher class provides a set of tools to hash a password.
 *
 *  In order to provide a security layer, this class uses a "salt" key, that can either be passed as argument in the
 *  class constructor (necessary to verify a password matching), or automatically generated with std constructor
 *  (suggested in case of password generation).
 */
public class PasswordHasher {

    /**
     *  A salt key, used to hash the password   */
    private final byte[] salt;

    /**
     * PasswordHasher standard constructor, generates a new salt value
     *
     * @throws PwdHasherException class' standard exception
     */
    public PasswordHasher() throws PwdHasherException {
        this(generateSalt());
    }

    /**
     * PasswordHasher constructor.
     *
     * @param salt a key used to encrypt the password.
     */
    public PasswordHasher(byte[] salt) {
        this.salt = salt;
    }

    /**
     * This method hashes a password string with PBKDF2 encoding function.
     *
     * @param password the string to encode
     * @return a String containing the encoded password
     * @throws PwdHasherException class' standard exception
     */
    public String hashPwd(String password) throws PwdHasherException {
        KeySpec spec;

        spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        byte[] hash;

        try {
            hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new PwdHasherException(e.getMessage());
        }

        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * @return the salt key used for encryption
     */
    public byte[] getKey() {
        return salt;
    }

    /**
     * Method to generate a new salt key, using a SecureRandom.
     *
     * @return a new random salt key.
     * @throws PwdHasherException class' standard exception
     */
    private static byte[] generateSalt() throws PwdHasherException {
        byte[] newSalt = new byte[16];

        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
            secureRandom.nextBytes(newSalt);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new PwdHasherException(e.getMessage());
        }

        return newSalt;
    }
}
