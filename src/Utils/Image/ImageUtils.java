package Utils.Image;

import Utils.Constants;
import Utils.NetUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageUtils {

    public static void InitializeBackground(JPanel panel, String fileName, int width, int height) {
        try {
            Image image = ImageIO.read(new File(System.getProperty("user.dir") + "/materials/background/" + fileName))
                    .getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            JLabel background = new JLabel();
            background.setIcon(icon);
            background.setBounds(0, 0, width, height);
            panel.add(background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JLabel GetBackground(JPanel panel, String fileName, int width, int height) {
        JLabel background = new JLabel();
        try {
            Image image = ImageIO.read(new File(System.getProperty("user.dir") + "/materials/background/" + fileName))
                    .getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            background.setIcon(icon);
            background.setBounds(0, 0, width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return background;
    }

    public static ArrayList<ImageObject> ImagesFromURL(String word) {
        ArrayList<ImageObject> imageList = new ArrayList<>();
        NetUtils.DoGetRequest(Constants.IMAGE_URL(word), result -> {
            if(result == null) return;
            JSONObject root = new JSONObject();
            try {
                JSONParser jParser = new JSONParser();
                root = (JSONObject) jParser.parse(result);
                JSONArray hits = (JSONArray) root.get("hits");
                for(var item : hits) {
                    JSONObject object = (JSONObject) item;
                    imageList.add(
                        new ImageObject(
                            object.get("webformatURL").toString(),
                            object.get("tags").toString()
                        )
                    );
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        return imageList;
    }
}
