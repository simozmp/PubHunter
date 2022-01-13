package logic;
import logic.exception.PwdHasherException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordHasher {
    private final SecureRandom secureRandom;
    private byte[] salt;
    private SecretKeyFactory secretKeyFactory;
    private Base64.Encoder encoder;

    public PasswordHasher() throws PwdHasherException {
        salt = new byte[16];
        secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        try {
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new PwdHasherException("The password hasher is unable to get SecretKeyFactory instance (NoSuchAlgorithmException).");
        }
        encoder = Base64.getEncoder();
    }

    public PasswordHasher(byte[] salt) throws PwdHasherException {
        this.salt = salt;
        secureRandom = new SecureRandom();

        try {
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new PwdHasherException("The password hasher is unable to get SecretKeyFactory instance (NoSuchAlgorithmException).");
        }
        encoder = Base64.getEncoder();
    }

    public String hashPwd(String password) throws PwdHasherException {
        KeySpec spec;
        spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        byte[] hash = new byte[0];

        try {
            hash = secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new PwdHasherException(e.getMessage());
        }

        return encoder.encodeToString(hash);
    }

    public byte[] getSalt() {
        return salt;
    }
}
