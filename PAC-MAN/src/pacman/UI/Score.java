package pacman.UI;

import java.awt.*;

public class Score {
    private int score;

    public void increment(int delta) {
        score+=delta;
    }

    public void drawScore(Graphics2D g2d) {
        g2d.setColor(new Color(255, 104, 0));
        String s = "Score: " + score;
        g2d.drawString(s, 500 / 2 + 96, 16);
    }

    public int getScore() {
        return score;
    }
}
