package logic.dao;

import logic.dao.implementation.UserDAOImpl;
import logic.exception.DAOException;
import logic.exception.PwdHasherException;
import logic.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * A test class for UserDAO.
 *
 * @author simozmp
 */
class UserRemoteDBDAOTest {
    @Test
    void testIOMatching() throws AddressException, DAOException, PwdHasherException {

        //  Setting up sample user data to write in persistence
        String kentFirstName = "Kent";
        String kentLastName = "Beck";
        String kentEmail = "kent_beck@randomain.com";
        String kentPwd = "some_random_password_with_letters_and_symbols";
        String erichFirstName = "Erich";
        String erichLastName = "Gamma";
        String erichEmail = "erich_gamma@randomain.com";
        String erichPwd = "some_other_random_password_alphanum3ric";

        //  Creating new users
        User inputKentUser = new User(kentFirstName, kentLastName, new InternetAddress(kentEmail), kentPwd);
        User inputErichUser = new User(erichFirstName, erichLastName, new InternetAddress(erichEmail), erichPwd);

        //  DAO instantiation
        UserDAO dao = new UserDAOImpl();

        //  Writing users into persistence
        dao.insert(inputKentUser);
        dao.insert(inputErichUser);

        //  Reading users into new objects
        User outputKentUser = dao.readUserByEmail(kentEmail);
        User outputErichUser = dao.readUserByEmail(erichEmail);

        //  Cleaning db
        dao.removeUserByEmail(kentEmail);
        dao.removeUserByEmail(erichEmail);

        //  Data integrity check
        //      For first user
        Assertions.assertEquals(inputKentUser.getFirstName(), outputKentUser.getFirstName());
        Assertions.assertEquals(inputKentUser.getLastName(), outputKentUser.getLastName());
        Assertions.assertEquals(inputKentUser.getEmailAddress(), outputKentUser.getEmailAddress());
        Assertions.assertEquals(inputKentUser.getPasswordTrace().matches(kentPwd), outputKentUser.getPasswordTrace().matches(kentPwd));
        //      For second user
        Assertions.assertEquals(inputErichUser.getFirstName(), outputErichUser.getFirstName());
        Assertions.assertEquals(inputErichUser.getLastName(), outputErichUser.getLastName());
        Assertions.assertEquals(inputErichUser.getEmailAddress(), outputErichUser.getEmailAddress());
        Assertions.assertEquals(inputErichUser.getPasswordTrace().matches(erichPwd), outputErichUser.getPasswordTrace().matches(erichPwd));
    }
}