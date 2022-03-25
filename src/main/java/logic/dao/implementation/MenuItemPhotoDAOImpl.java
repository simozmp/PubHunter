package logic.dao.implementation;

import logic.dao.MenuItemPhotoDAO;
import logic.exception.DAOException;
import logic.exception.DAOInsertOnExistingItemException;
import logic.utils.ImageUtils;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItemPhotoDAOImpl extends JDBCDataAccessObjectImpl implements MenuItemPhotoDAO {

    @Override
    public Image getByItemId(int itemId) throws DAOException {

        Image result = null;

        this.connect();

        String query = "SELECT photo FROM menu_items_photos WHERE menu_item = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, itemId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                result = ImageUtils.bytesToBufferedImage(resultSet.getBytes(1));

        } catch (SQLException e) {
            throw new DAOException("SQLException in MenuItemPhotoDAOImpl.getByItemId()");
        } finally {
            this.disconnect();
        }

        return result;
    }

    @Override
    public void insert(int itemId, Image photo) throws DAOException {
        this.connect();

        String query = "INSERT INTO menu_items_photos(menu_item, photo) VALUES (?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, itemId);
            preparedStatement.setBytes(2, ImageUtils.imageToBytes(photo));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if(e.getMessage().contains("UNIQUE constraint failed"))
                throw new DAOInsertOnExistingItemException("Photo for item already in db.");
            else
                throw new DAOException("SQLException in MenuItemPhotoDAOImpl.insert(). " +
                    "Message: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();
    }

    @Override
    public void update(int itemId, Image photo) throws DAOException {
        this.connect();

        String query = "UPDATE menu_items_photos SET photo = ? WHERE menu_item = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setBytes(1, ImageUtils.imageToBytes(photo));
            preparedStatement.setInt(2, itemId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("SQLException in MenuItemPhotoDAOImpl.insert(). " +
                    "Message: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();
    }
}
