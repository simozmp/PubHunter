package logic.dao;

import logic.exception.DAOException;
import logic.model.PasswordTrace;
import logic.model.User;

public interface UserDAO extends DAO {
    void insert(String firstName, String lastName, String email, PasswordTrace password) throws DAOException;

    void insert(User user) throws DAOException;

    User readUserByEmail(String email) throws DAOException;

    void removeUserByEmail(String userEmail) throws DAOException;

    User getManagerByRestaurantId(int id) throws DAOException;

    byte[] serializePasswordTrace(PasswordTrace passwordTrace) throws DAOException;

    PasswordTrace deserializePasswordTrace(byte[] serializedPasswordTrace) throws DAOException;
}
