package pacman.PowerUp;

import pacman.characters.CharacterPacman;
import pacman.core.CoordinateConverter;
import pacman.characters.Ghost.GhostsCollection;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;


public class PowerUpCollection {
    private final ArrayList<PowerUp> powerUps;
    private final int MAX_POWER_UPS = 3;
    Random random = new Random();
    private GhostsCollection ghostsCollection;

    public void init() {
        powerUps.clear();
    }

    public PowerUpCollection(GhostsCollection ghostsCollection) {
        this.ghostsCollection = ghostsCollection;
        this.powerUps = new ArrayList<PowerUp>();


        Runnable addPowerUpToGame = () -> {
            while(true) {
                try {
                    Thread.sleep(10000);
                    addPowerUp();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };

        Thread addPowerUp = new Thread(addPowerUpToGame);
        addPowerUp.start();
    }


    public void addPowerUp() {
        if (powerUps.size() < MAX_POWER_UPS) {
            var newPowerUp = createPowerUp();
            if (newPowerUp != null)
                powerUps.add(createPowerUp());
        }
    }


    public PowerUp createPowerUp() {
        int randomPowerUp = random.nextInt(5) + 1;
        var ghost = ghostsCollection.getRandomGhost();
        if (ghost == null)
            return null;
        var x = CoordinateConverter.fromScreenCoordinate(ghost.getX());
        var y = CoordinateConverter.fromScreenCoordinate(ghost.getY());
        switch (randomPowerUp) {
            case 1:
                return new CherryPowerUp(x, y);
            case 2:
                return new GapplePowerUp(x, y);
            case 3:
                return new BootsPowerUp(x, y);
            case 4:
                return new ShieldPowerUp(x, y);
            case 5:
                return new LivePowerUp(x, y);
            default:
                return null;
        }
    }

    public void drawPowerUp(Graphics2D g2d, ImageObserver imageObserver) {
        for (PowerUp powerUp : powerUps) {
            powerUp.drawPowerUp(g2d, imageObserver);
        }
    }


    public PowerUp findInPosition(CharacterPacman pacman) {
        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);
            if (powerUp.meet(pacman)) {
                powerUps.remove(i);
                return powerUp;
            }
        }
        return null;
    }
}