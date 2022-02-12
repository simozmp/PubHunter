package logic.dao;

import logic.exception.DAOException;
import logic.model.PasswordTrace;
import logic.model.User;

/**
 * Data access object class for User entity class.
 */
public interface UserDAO extends JDBCDataAccessObject {

    /**
     * Insertion operation.
     *
     * @param user the User instance to be written in the db
     * @throws DAOException if error occurs. Details in exception message
     */
    void insert(User user) throws DAOException;

    /**
     * Reading operation.
     *
     * @param email the email address of the requested user
     * @throws DAOException if error occurs. Details in exception message
     */
    User readUserByEmail(String email) throws DAOException;

    /**
     * Remove operation.
     *
     * @param userEmail the email address of the requested user
     * @throws DAOException if error occurs. Details in exception message
     */
    void removeUserByEmail(String userEmail) throws DAOException;

    /**
     * Reading operation by id of the user managed restaurant.
     *
     * @param id the id of the restaurant managed by the requested user
     * @return found admin User
     * @throws DAOException if error occurs. Details in exception message
     */
    User getManagerByRestaurantId(int id) throws DAOException;
}
