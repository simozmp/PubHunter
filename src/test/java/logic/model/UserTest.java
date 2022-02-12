package logic.model;

import logic.exception.PwdHasherException;
import org.junit.jupiter.api.Test;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A test class for User.
 *
 * @author simozmp
 */
class UserTest {

    private static final String TEST_FIRST_NAME = "Mister";
    private static final String TEST_LAST_NAME = "Test";
    private static final String TEST_EMAIL_ADDRESS = "mister.test@testhost.com";
    private static final String TEST_PASSWORD = "TestPassw0rd";


    User getTestUser() throws AddressException, PwdHasherException {
        return new User(TEST_FIRST_NAME, TEST_LAST_NAME, new InternetAddress(TEST_EMAIL_ADDRESS), TEST_PASSWORD);
    }

    @Test
    void testPwd() throws AddressException, PwdHasherException {
        assertTrue(getTestUser().getPasswordTrace().matches(TEST_PASSWORD));
    }

    @Test
    void getId() throws AddressException, PwdHasherException {
        assertTrue(getTestUser().getId().matches(TEST_EMAIL_ADDRESS));
    }

    @Test
    void getFirstName() throws AddressException, PwdHasherException {
        assertTrue(getTestUser().getFirstName().matches(TEST_FIRST_NAME));
    }

    @Test
    void getLastName() throws AddressException, PwdHasherException {
        assertTrue(getTestUser().getLastName().matches(TEST_LAST_NAME));
    }

    @Test
    void getEmailAddress() throws AddressException, PwdHasherException {
        assertTrue(getTestUser().getEmailAddress().toString().matches(TEST_EMAIL_ADDRESS));
    }
}