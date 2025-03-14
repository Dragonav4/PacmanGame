package pacman.PowerUp;

import pacman.core.Game;

public class GapplePowerUp extends PowerUp {

    public GapplePowerUp(int x, int y) {
        super(x, y);
    }

    @Override
    protected String getSpriteName() {
        return "GoldenApple.png";
    }

    @Override
    public void apply(Game game) {
        game.startHunt();

    }
}


