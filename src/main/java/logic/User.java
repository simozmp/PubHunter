package logic;

import logic.exception.PwdHasherException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class User implements Customer {

    private String firstName;
    private String lastName;
    private InternetAddress eMailAddress;
    private Password password;

    public User(String firstName, String lastName, InternetAddress eMailAddress, String password) throws AddressException, PwdHasherException {
        eMailAddress.validate();

        this.firstName = firstName;
        this.lastName = lastName;
        this.eMailAddress = eMailAddress;
        PasswordHasher passwordHasher = new PasswordHasher();
        try {
            this.password = new Password(passwordHasher.hashPwd(password), passwordHasher.getSalt());
        } catch (PwdHasherException e) {
            e.printStackTrace();
        }

    }

    public User(String firstName, String lastName, String eMailAddress, String password) throws AddressException, PwdHasherException {
        this(firstName, lastName, new InternetAddress(eMailAddress), password);
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
}
