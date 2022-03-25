package logic.dao;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface TheCocktailDBDataAccessObject {
    BufferedImage readPhotoById(int theCocktailDBId) throws IOException;
    String readPhotoPathById(int theCocktailDBId) throws IOException;
}
