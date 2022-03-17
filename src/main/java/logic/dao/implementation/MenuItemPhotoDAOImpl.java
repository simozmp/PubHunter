package logic.dao.implementation;

import logic.dao.MenuItemPhotoDAO;
import logic.exception.DAOException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
                result = getPhotoBufferedImage(resultSet.getBytes(1));

        } catch (SQLException e) {
            throw new DAOException("SQLException in MenuItemPhotoDAOImpl.getByItemId()");
        } finally {
            this.disconnect();
        }

        this.disconnect();

        return result;
    }

    @Override
    public void insert(int itemId, Image photo) throws DAOException {
        this.connect();

        String query = "INSERT INTO menu_items_photos(menu_item, photo) VALUES (?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, itemId);
            preparedStatement.setBytes(2, getPhotoByteArray(photo));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("SQLException in MenuItemPhotoDAOImpl.insert(). " +
                    "Message: " + e.getMessage());
        } finally {
            this.disconnect();
        }

        this.disconnect();
    }

    public byte[] getPhotoByteArray(Image photo) throws DAOException {
        byte[] result = null;

        //  Converting Image to BufferedImage
        BufferedImage bufferedImage = new BufferedImage(photo.getWidth(null), photo.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(photo, 0, 0, null);
        graphics2D.dispose();

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

            result = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new DAOException("There was an error during image encoding");
        }

        return result;
    }

    public BufferedImage getPhotoBufferedImage(byte[] photo) throws DAOException {
        BufferedImage result = null;

        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(photo)) {
            result = ImageIO.read(byteArrayInputStream);
        } catch (IOException e) {
            throw new DAOException("There was an error during image decoding");
        }

        return result;
    }
}
