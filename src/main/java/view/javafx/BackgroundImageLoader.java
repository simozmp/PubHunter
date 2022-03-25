package view.javafx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.dao.TheCocktailDBDataAccessObject;
import logic.dao.implementation.TheCocktailDBDataAccessObjectImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class BackgroundImageLoader implements Runnable {

    private final int theCocktailDBId;
    private final ImageView imageView;
    private static final HashMap<Integer, javafx.scene.image.Image> cache = new HashMap<>();

    public BackgroundImageLoader(int theCocktailDBId, ImageView imageView) {
        this.theCocktailDBId = theCocktailDBId;
        this.imageView = imageView;
    }

    @Override
    public void run() {

        Image jfxImage = null;

        TheCocktailDBDataAccessObject theCocktailDBDataAccessObject = new TheCocktailDBDataAccessObjectImpl();

        try {
            synchronized (this) {
                if(cache.containsKey(theCocktailDBId)) {
                    jfxImage = cache.get(theCocktailDBId);
                } else {
                    jfxImage = new javafx.scene.image.Image(Objects.requireNonNull(this.getClass().getResourceAsStream("loading.png")));

                    imageView.setImage(jfxImage);

                    jfxImage = new javafx.scene.image.Image(theCocktailDBDataAccessObject.readPhotoPathById(theCocktailDBId));
                    cache.put(theCocktailDBId, jfxImage);
                }
            }

        } catch (IOException e) {   // Raised if theCocktailDB gave not results
            jfxImage = new javafx.scene.image.Image(Objects.requireNonNull(this.getClass().getResourceAsStream("drink.png")));
        } finally {
            imageView.setImage(jfxImage);
            Thread.currentThread().interrupt();
        }
    }
}