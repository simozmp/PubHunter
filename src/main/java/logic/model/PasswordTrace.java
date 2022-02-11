package logic.model;

import logic.exception.DAOException;
import logic.exception.PwdHasherException;

import java.io.*;

/**
 * This class represents a password trace, a set of a hash String and a key used to encrypt it.
 */
public class PasswordTrace implements Serializable {

    /**
        This is used to identify class during I/O ({de,}serialization)          */
    private static final long serialVersionUID = 6529685098267757690L;

    /**
        The hashed password                                                     */
    private String hash;

    /**
        The key used for hashing the password                                   */
    private byte[] key;

    /**
     * @param hash the password hashed with a PasswordHasher object.
     * @param key the hashing key.
     */
    public PasswordTrace(String hash, byte[] key) {
        this.hash = hash;
        this.key = key.clone();
    }

    public static PasswordTrace deserialize(byte[] serializedPasswordTrace) throws IOException, ClassNotFoundException {
        PasswordTrace deserializedPasswordTrace;

        ByteArrayInputStream passwordByteArray = new ByteArrayInputStream(serializedPasswordTrace);

        ObjectInputStream objectInputStream = new ObjectInputStream(passwordByteArray);

        deserializedPasswordTrace = (PasswordTrace) objectInputStream.readObject();

        return deserializedPasswordTrace;
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

    public byte[] serialize() throws IOException {
        byte[] result = null;


        ByteArrayOutputStream passwordByteArray = new ByteArrayOutputStream();

        ObjectOutputStream passwordOutputStream = new ObjectOutputStream(passwordByteArray);
        passwordOutputStream.writeObject(this);
        passwordOutputStream.flush();

        result = passwordByteArray.toByteArray();

        return result;
    }
}
