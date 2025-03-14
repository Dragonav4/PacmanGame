package pacman.UI;
import Utils.FileUtils;

import java.awt.*;
import java.awt.image.ImageObserver;

public class LiveCounter {
    private static final Image heart = FileUtils.loadImage("/pacman/images/sprites/heart.png");
    private int lives=3;

    public void decrement() {
        lives--;
    }

    public void increment() {
        lives++;
    }

    public void drawLivesCounter(Graphics2D g2d, ImageObserver imageObserver) {
        for (int i = 0; i < lives; i++) {
            g2d.drawImage(heart, i * 28 + 8,  + 1, imageObserver);
        }
    }

    public int getLives() {
        return lives;
    }
}
