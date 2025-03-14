package Utils;
import pacman.Pacman;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Font;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileUtils {
    public static Font loadFont(String path, int style, int size) {
        URL resourceLocation = Pacman.class.getResource(path);
        try {
            var pathStr = resourceLocation.getPath();
            InputStream stream = new FileInputStream(resourceLocation.getPath());
            Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
            return font.deriveFont(style, size);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public static Image loadImage(String path) {
        var resourceLocation = Pacman.class.getResource(path);
        if (resourceLocation == null) {
            System.err.println("Resource not found: " + path);
        } else {
            try {
                var sprite = ImageIO.read(resourceLocation);
                if (sprite == null) {
                    System.err.println("Failed to load image: " + resourceLocation);
                } else {
                    return sprite;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to load image: " + resourceLocation);
            }
        }
        return null;
    }

    public static ImageIcon loadImageIcon(String path) {
        var imgURL = Pacman.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}
