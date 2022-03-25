package logic.dao.implementation;

import logic.dao.TheCocktailDBDataAccessObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TheCocktailDBDataAccessObjectImpl implements TheCocktailDBDataAccessObject {
    @Override
    public BufferedImage readPhotoById(int theCocktailDBId) throws IOException {
        URL remotePngPath = new URL(readPhotoPathById(theCocktailDBId));
        return ImageIO.read(remotePngPath);
    }

    @Override
    public String readPhotoPathById(int theCocktailDBId) throws IOException {

        JSONObject recordJson = readJsonFromUrl("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + theCocktailDBId);

        JSONObject drinkJson = (JSONObject) recordJson.getJSONArray("drinks").get(0);
        return drinkJson.getString("strDrinkThumb");
    }

    public static JSONObject readJsonFromUrl(String url) throws JSONException, IOException {
        String jsonText;
        InputStream is = new URL(url).openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        jsonText = readAll(rd);
        return new JSONObject(new JSONTokener(jsonText));
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
