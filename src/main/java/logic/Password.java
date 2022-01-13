package logic;

import logic.exception.PwdHasherException;

import java.security.spec.InvalidKeySpecException;

public class Password {

    private String hash;
    private byte[] salt;

    public Password(String hash, byte[] salt) {
        this.hash = hash;
        this.salt = salt;
    }

    public boolean matches(String candidate) throws PwdHasherException {
        PasswordHasher hasher = new PasswordHasher(salt);

        return hasher.hashPwd(candidate).matches(hash);
    }
}
