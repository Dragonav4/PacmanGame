package pacman.PowerUp;
import Utils.FileUtils;
import pacman.characters.CharacterPacman;
import pacman.core.CoordinateConverter;
import pacman.core.Game;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public abstract class PowerUp {
    int x;
    int y;

    private Image sprite;
    private ArrayList<PowerUp> powerUps;


    public PowerUp(int x, int y) {
        this.x = CoordinateConverter.toScreenCoordinate(x);
        this.y = CoordinateConverter.toScreenCoordinate(y);
        var toolkit = Toolkit.getDefaultToolkit();



        sprite = FileUtils.loadImage("/pacman/images/sprites/powerUp/" + getSpriteName());

    }
    protected abstract String getSpriteName();

    public abstract void apply(Game game);


    public void drawPowerUp(Graphics2D g2d, ImageObserver imageObserver) {
        g2d.drawImage(sprite, x, y, 24, 24, imageObserver);
    }

    public boolean meet(CharacterPacman pacman) {
            var PowerUpRectangle = new Rectangle(x, y, CoordinateConverter.block_size, CoordinateConverter.block_size);
            var pacmanRectangle = new Rectangle(pacman.getX(), pacman.getY(), CoordinateConverter.block_size, CoordinateConverter.block_size);
            return PowerUpRectangle.intersects(pacmanRectangle);
    }
}

