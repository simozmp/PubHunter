package logic.utils;

import logic.exception.DAOException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {
    private ImageUtils() {}

    public static byte[] imageToBytes(Image image) throws DAOException {
        byte[] result;

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(getBuffered(image), "png", byteArrayOutputStream);

            result = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new DAOException("There was an error during image encoding");
        }

        return result;
    }

    public static BufferedImage getBuffered(Image image) {
        BufferedImage result;

        if(image instanceof BufferedImage bufferedImage)
            result = bufferedImage;
        else {
            BufferedImage newBufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D graphics2D = newBufferedImage.createGraphics();
            graphics2D.drawImage(image, 0, 0, null);
            graphics2D.dispose();

            result = newBufferedImage;
        }

        return result;
    }

    public static BufferedImage bytesToBufferedImage(byte[] photo) throws DAOException {
        BufferedImage result;

        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(photo)) {
            result = ImageIO.read(byteArrayInputStream);
        } catch (IOException e) {
            throw new DAOException("There was an error during image decoding");
        }

        return result;
    }
}
