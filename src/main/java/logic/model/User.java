package logic.model;

import logic.exception.PwdHasherException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Model class for a user, it contains data such as first and last name, email address, and a password trace.
 */
public class User implements Customer {

    private final String firstName;
    private final String lastName;
    private final InternetAddress eMailAddress;
    private final PasswordTrace passwordTrace;

    public User(String firstName, String lastName, InternetAddress eMailAddress, String password)
            throws AddressException, PwdHasherException {
        eMailAddress.validate();

        this.firstName = firstName;
        this.lastName = lastName;
        this.eMailAddress = eMailAddress;
        PasswordHasher passwordHasher = new PasswordHasher();
        this.passwordTrace = new PasswordTrace(passwordHasher.hashPwd(password), passwordHasher.getKey());
    }

    public User(String firstName, String lastName, String eMailAddress, PasswordTrace passwordTrace)
            throws AddressException {

        //  Preliminary operation: verify mail string integrity
        InternetAddress mail = new InternetAddress(eMailAddress);
        mail.validate();

        this.firstName = firstName;
        this.lastName = lastName;
        this.eMailAddress = mail;
        this.passwordTrace = passwordTrace;
    }

    @Override
    public String getId() {
        return eMailAddress.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public InternetAddress getEmailAddress() {
        return this.eMailAddress;
    }

    public PasswordTrace getPasswordTrace() {
        return this.passwordTrace;
    }
}
