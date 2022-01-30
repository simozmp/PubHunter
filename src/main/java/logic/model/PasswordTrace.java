package logic.model;

import logic.exception.PwdHasherException;

import java.io.Serializable;

/**
 * This class represents a password trace, a set of a hash String and a key used to encrypt it.
 */
public class PasswordTrace implements Serializable {

    /**
        The hashed password                     */
    private String hash;

    /**
        The key used for hashing the password   */
    private byte[] key;

    /**
     * @param hash the password hashed with a PasswordHasher object.
     * @param key the hashing key.
     */
    public PasswordTrace(String hash, byte[] key) {
        this.hash = hash;
        this.key = key.clone();
    }

    /**
     * Method to check for a password match.
     *
     * @param candidate The candidate password string.
     * @return Boolean value for matching.
     * @throws PwdHasherException PasswordHasher standard exception.
     */
    public boolean matches(String candidate) throws PwdHasherException {
        PasswordHasher hasher = new PasswordHasher(key);

        return hasher.hashPwd(candidate).equals(hash);
    }
}
