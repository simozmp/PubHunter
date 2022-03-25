package view.javafx;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.bean.MenuItemBean;
import logic.utils.ImageUtils;

import java.util.Objects;

public interface PhotoItemListCell {

    default void drawItemPhoto(ImageView imageView, MenuItemBean bean) {
        Image jfxImage = null;

        synchronized (this) {
            try {   //  Trying to read photo from DB
                jfxImage = SwingFXUtils.toFXImage(ImageUtils.getBuffered(
                        Objects.requireNonNull(bean.getPhoto())), null);
            } catch (NullPointerException npe) {    //  If no photo found,
                if (bean.getTheCocktailDBId() != -1) //  and item is a "the cocktail db" drink item,
                    //  Fetch image in background
                    new Thread(new BackgroundImageLoader(bean.getTheCocktailDBId(), imageView)).start();
                else
                    jfxImage = new Image(Objects.requireNonNull(JFXMLApplication.class.getResourceAsStream("dish.png")));
            } finally {
                imageView.setImage(jfxImage);
            }
        }
    }
}
