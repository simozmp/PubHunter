package logic.dao;

import logic.exception.DAOException;

import java.awt.*;

public interface MenuItemPhotoDAO extends JDBCDataAccessObject {

    Image getByItemId(int itemId) throws DAOException;

    void insert(int itemId, Image photo) throws DAOException;
}
