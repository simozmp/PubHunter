package logic.dao.implementation;

import logic.exception.DAOException;
import logic.exception.DAOInsertOnExistingItemException;
import logic.exception.DBInconsistencyException;
import logic.model.PasswordTrace;
import logic.model.User;

import javax.mail.internet.AddressException;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;

public class UserDAOImpl extends DAOImpl implements logic.dao.UserDAO {
    @Override
    public void insert(String firstName, String lastName, String email, PasswordTrace password) throws DAOException {
        this.connect();

        byte[] serializedPwd = serializePasswordTrace(password);

        String query = "INSERT INTO users(first_name, last_name, email, pwd, sign_up_datetime)" +
                "VALUES (?,?,?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            //  Parsing arguments into query
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setBytes(4, Base64.getEncoder().encode(serializedPwd));
            preparedStatement.setString(5, LocalDate.now() + "%" + LocalTime.now().toString());

            //  Executing query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if(e.getMessage().contains("UNIQUE constraint failed: users.email"))
                throw new DAOInsertOnExistingItemException("User with email \"" + email + "\" is already registered into the db");
            else
                throw new DAOException("Unexpected SQLException in UserDAO with message: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();
    }

    @Override
    public void insert(User user) throws DAOException {
        this.insert(user.getFirstName(), user.getLastName(), user.getEmailAddress().toString(), user.getPasswordTrace());
    }

    @Override
    public User readUserByEmail(String email) throws DAOException {
        this.connect();

        String query = "SELECT first_name, last_name, email, pwd FROM users WHERE email = ?";

        ResultSet resultSet;

        String userWithEmailString = "User with email \"";

        User user = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            //  Parsing arguments into query
            preparedStatement.setString(1, email);

            //  Executing query
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            String userFirstName = resultSet.getString("first_name");
            String userLastName = resultSet.getString("last_name");
            String userEmail = resultSet.getString("email");
            PasswordTrace userPwd = deserializePasswordTrace(
                    Base64.getDecoder().decode(resultSet.getBytes("pwd")));

            user = new User(userFirstName, userLastName, userEmail, userPwd);
        } catch (SQLException e) {
            throw new DAOException("Unexpected SQLException in UserDAO with message: " + e.getMessage());
        } catch (AddressException e) {
            throw new DBInconsistencyException(userWithEmailString + email +
                    "\" has been found, but the mail is not a valid InternetAddress.");
        } finally {
            this.disconnect();
        }

        this.disconnect();

        return user;
    }

    @Override
    public void removeUserByEmail(String userEmail) throws DAOException {
        this.connect();

        String query = "DELETE FROM users WHERE email=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userEmail);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DB connection error. (SQLException with message \"" + e.getMessage() + "\")");
        } finally {
            this.disconnect();
        }

        this.disconnect();
    }

    @Override
    public User getManagerByRestaurantId(int id) throws DAOException {
        this.connect();

        String query = "SELECT * FROM (users JOIN restaurants on manager = email) WHERE id = ?";

        User manager = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            PasswordTrace passwordTrace = deserializePasswordTrace(
                    Base64.getDecoder().decode(resultSet.getBytes("pwd")));

            manager = new User(resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    passwordTrace);

        } catch (AddressException e) {
            throw new DBInconsistencyException("Manager with id \"" + id +
                "\" has been found, but the mail is not a valid InternetAddress.");
        } catch (SQLException e) {
            throw new DAOException("DB connection error. (SQLException with message \"" + e.getMessage() +"\")");
        } finally {
            this.disconnect();
        }

        this.disconnect();

        return manager;
    }

    @Override
    public byte[] serializePasswordTrace(PasswordTrace passwordTrace) throws DAOException {
        byte[] result = null;

        try {
            ByteArrayOutputStream passwordByteArray = new ByteArrayOutputStream();

            ObjectOutputStream passwordOutputStream = new ObjectOutputStream(passwordByteArray);
            passwordOutputStream.writeObject(passwordTrace);
            passwordOutputStream.flush();

            result = passwordByteArray.toByteArray();
        } catch (IOException e) {
            throw new DAOException("Password encoding error: IOException");
        }

        return result;
    }

    @Override
    public PasswordTrace deserializePasswordTrace(byte[] serializedPasswordTrace) throws DAOException {

        PasswordTrace deserializedPasswordTrace;

        ByteArrayInputStream passwordByteArray = new ByteArrayInputStream(serializedPasswordTrace);

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(passwordByteArray);
            deserializedPasswordTrace = (PasswordTrace) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new DAOException("Password decoding error: ClassNotFoundException");
        } catch (ClassCastException e) {
            throw new DAOException("Password decoding error: ClassCastException");
        } catch (IOException e) {
            throw new DAOException("Password decoding error: IOException");
        }

        return deserializedPasswordTrace;
    }
}
