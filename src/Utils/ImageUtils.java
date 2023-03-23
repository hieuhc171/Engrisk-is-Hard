package Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static void InitializeBackground(JPanel panel, String fileName, int width, int height) {
        Image image = null;
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/materials/background/" + fileName))
                    .getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            JLabel background = new JLabel();
            background.setIcon(icon);
            background.setBounds(0, 0, width, height);
            panel.add(background);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
