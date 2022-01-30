package logic.model;

import logic.exception.PwdHasherException;
import org.junit.jupiter.api.Test;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testPwd() throws AddressException, PwdHasherException {
        String userFirstName = "kent";
        String userLastName = "Beck";
        String userEmail = "kent_beck@randomain.com";
        String userPwd = "random_password_123";

        User user = new User(userFirstName, userLastName, new InternetAddress(userEmail), userPwd);

        assertEquals(true, user.getPasswordTrace().matches(userPwd));
    }
}